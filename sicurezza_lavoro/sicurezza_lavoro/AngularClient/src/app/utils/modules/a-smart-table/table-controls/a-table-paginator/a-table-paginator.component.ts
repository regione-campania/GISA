import {
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ATableControl } from '../a-table-control.directive';

@Component({
  selector: 'a-table-paginator',
  templateUrl: './a-table-paginator.component.html',
  styleUrls: ['./a-table-paginator.component.scss'],
})
export class ATablePaginatorComponent extends ATableControl implements OnInit {
  @ViewChild('rowsPerPageControl', { static: true })
  rowsPerPageControl!: ElementRef;

  get rowsPerPage() {
    //selected by user
    return this.rowsPerPageControl.nativeElement.value;
  }
  get tbodyElem() {
    return this.aSmartTable.table?.nativeElement.querySelector(
      'tbody'
    ) as HTMLTableSectionElement;
  }

  private _pages: Page[] = [];
  get pages() {
    return this._pages;
  }
  private set pages(pages: Page[]) {
    this._pages = pages;
  }

  private _currentPage?: Page;
  get currentPage() {
    return this._currentPage;
  }

  private _previousPage?: Page;
  get previousPage() {
    return this._previousPage;
  }

  get firstPage() {
    return this.pages[0];
  }

  get lastPage() {
    return this.pages.length > 1
      ? this.pages[this.pages.length - 1]
      : undefined;
  }

  get middlePages() {
    let mp;
    if (this.pages.length >= 3 && this.currentPage) {
      mp = new Array<Page>();
      switch (this.pages.length) {
        case 3:
          mp = [this.pages[1]];
          break;
        case 4:
          mp = [this.pages[1], this.pages[2]];
          break;
        default:
          {
            // >= 5
            let mid = this.currentPage.pageNumber;
            if (mid < 2) mid = 2;
            else if (mid > this.pages.length - 3) mid = this.pages.length - 3;
            mp = [this.pages[mid - 1], this.pages[mid], this.pages[mid + 1]];
          }
          break;
      }
    }
    return mp;
  }

  reloadPages() {
    this.pages = [];
    const visibleContent = this.aSmartTable.content.filter((c) => c.v);
    const totalPages = Math.max(
      1,
      Math.ceil(visibleContent.length / this.rowsPerPage)
    );
    for (let i = 0; i < totalPages; i++) {
      this.pages.push({
        content: visibleContent.slice(
          i * this.rowsPerPage,
          (i + 1) * this.rowsPerPage
        ),
        pageNumber: i,
      });
    }
    this.openPage(0);
  }

  openPage(pageNumber: number | string) {
    if (typeof pageNumber === 'string') pageNumber = parseInt(pageNumber) - 1;
    if (isNaN(pageNumber)) return;
    pageNumber =
      pageNumber < 0
        ? 0
        : pageNumber >= this.pages.length
        ? this.pages.length - 1
        : pageNumber;
    if (this.currentPage) this._previousPage = this.currentPage;
    this._currentPage = this.pages[pageNumber];
  }

  ngOnInit(): void {
    this.reloadPages();
    this.aSmartTable.contentChange.subscribe(() => {
      this.reloadPages();
    });
  }
}

interface Page {
  content: any;
  pageNumber: number;
}
