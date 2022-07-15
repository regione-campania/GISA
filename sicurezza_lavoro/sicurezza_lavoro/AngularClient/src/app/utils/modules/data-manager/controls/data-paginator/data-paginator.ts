import { DataManagerService } from '../../data-manager.service';
import { DataPage, DataReflex } from '../../data-manager.types';

export class DataPaginator {
  private _itemsPerPage: number = 25;
  private _pages: DataPage[] = [];
  private _currentPage!: DataPage;
  private _previousPage?: DataPage;

  dm!: DataManagerService

  /* constructor(public dm: DataManagerService) {
    this.dm.paginator = this;
    this.reloadPages();
    this.dm.reflexChange.subscribe(() => {
      this.reloadPages();
    });
  } */

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

  reloadPages() {
    this.pages = [];
    const visibleContent = this.dm.reflex.filter((r: DataReflex) => r.v);
    const totalPages = Math.max(
      1,
      Math.ceil(visibleContent.length / this.itemsPerPage)
    );
    for (let i = 0; i < totalPages; i++) {
      this.pages.push({
        content: visibleContent.slice(
          i * this.itemsPerPage,
          (i + 1) * this.itemsPerPage
        ),
        pageNumber: i,
      });
    }
    this.openPage(0);
  }

  //getters & setters
  get pages() {
    return this._pages;
  }
  private set pages(pages: DataPage[]) {
    this._pages = pages;
  }

  get itemsPerPage() {
    return this._itemsPerPage;
  }

  set itemsPerPage(value: number) {
    this._itemsPerPage = value;
    this.reloadPages();
  }

  get currentPage() {
    return this._currentPage;
  }

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

  //returns middle pages (if any) of this pages
  get middlePages() {
    let mp;
    if (this.pages.length >= 3 && this.currentPage) {
      mp = new Array<DataPage>();
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
}
