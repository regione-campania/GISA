/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
if (!FrameworkI18N) {
  var FrameworkI18N = {
    message:
    {
      "file.required":                  "- File is required\r\n",
      "file.name.long":                 "- File name is too long\r\n",
      "imageFile.required":             "- File with images is required\r\n",
      "organization.required":          "- Organization name is required\r\n",
      "specify.alert.description":      "- Please specify an alert description\r\n",
      "data.list.required":             "- Date Listed is required\r\n",
      "serial.number.required":         "- Serial Number is required\r\n",
      "PortalRole.required":            "- Portal Role is required\r\n",
      "specify.blank.records":          "- Blank records cannot be saved\r\n",
      "name.required":                  "- Name is a required field.\r\n",
      "Form.not.submitted":             "The form could not be submitted.          \r\nPlease verify the following:\r\n\r\n",
      "subject.required":               "- A subject is required\r\n",
      "URL.invalid":                    "- URL entered is invalid.  Make sure there are no invalid characters\r\n",
      "commission.entered.invalid":     "- Commission entered is invalid\r\n",
      "select.CommunicationType":       "Please select a Communication Type",
      "low.estimate":                   "- Low Estimate cannot be higher than High Estimate\r\n",
      "check.phone":                    "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n",
      "File.not.submitted":             "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n",
      "are.you.sure":                   "Are you sure?",
      "check.url":                      "- URL entered is invalid.  Make sure there are no invalid characters\r\n",
      "specify.type":                   "- Please specify a type\r\n",
      "sure.select.account":            "- Make sure you select an account.\r\n",
      "select.account.types":           "- Please select one or more Account Types.\r\n",
      "delete.preference":              "Are you sure you would like to delete the preference",
      "specify.alert.type":             "- Please specify an alert type\r\n",
      "Fileinfo.not.submitted":         "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n",
      "check.email":                    "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n",
      "check.form":                     "Form could not be saved, please check the following:\r\n\r\n",
      "check.send.email":               "The message could not be sent, please check the following:\r\n\r\n",
      "no.create.relationship":         "Could not create the relationship, please check the following:\r\n\r\n",
      "provider.needs.relationship":    "- A provider needs to be selected to form the relationship\r\n",
      "Name.required":                  "- Name is required\r\n",
      "AlertDate.before.today":         "Alert Date is before today's date\r\n",
      "Subject.required":               "- Subject is required\r\n",
      "relationshiptype.needs.selected":"- A relationship type needs to be selected\r\n",
      "Filename.required":              "- Filename is required\r\n",
      "specify.activity.length.format": "- Check that the activity length is entered correctly\r\n",
      "specify.alert.date":             "- Please specify an alert date\r\n",
      "specify.activity.start.date":    "- Please specify an activity start date\r\n",
      "specify.activity.end.date":      "- Please specify an activity end date\r\n",
      "specify.activity.start.date.format": "- Check that the activity start date is entered correctly\r\n",
      "specify.activity.end.date.format":   "- Check that the activity end date is entered correctly\r\n",
      "activity.end.must.later.start.date":  "- Activity end date must be at a later date than start date!\r\n",
      "select.activity.participant.or.remove.notification": "- Please select at least one activity participant or remove email notification!\r\n",
      "specify.followup.length.format": "- Check that the followup length is entered correctly\r\n",
      "specify.followup.start.date":    "- Please specify a followup start date\r\n",
      "specify.followup.end.date":      "- Please specify a followup end date\r\n",
      "specify.followup.start.date.format": "- Check that the followup start date is entered correctly\r\n",
      "specify.followup.end.date.format":   "- Check that the followup end date is entered correctly\r\n",
      "followup.end.must.later.start.date":  "- Followup end date must be at a later date than start date!\r\n",
      "select.followup.participant.or.remove.notification": "- Please select at least one followup participant or remove email notification!\r\n",
      "specify.opportunity.type":       "- Please select an opportunity type (account or contact)\r\n",
      "check.ticket.issue.entered":     "- Check that issue is entered\r\n",
      "check.ticket.contact.entered":   "- Check that contact is selected\r\n",
      "title.required":                 "- Title is a required field\r\n",
      "description.required":           "- Description is a required field\r\n",
      "startdate.required":             "- Start Date is a required field\r\n",
      "Documents.team.role":            "(Role)",
      "Documents.team.dept":            "(Dept)",
      "check.quote.email.entered":      "- Check that email address is entered\r\n",
      "check.quote.phone.entered":      "- Check that a valid phone number is entered\r\n",
      "check.quotes.notes.empty":       "Please enter the notes to be saved.",
      "check.quote.product.name":       "- Please specify the product name\r\n",
      "none.selected":                  "None Selected",
      "check.priceamount.blank":        "- The price can not be blank\r\n",
      "check.quote.extendedprice.blank":"- The extended price can not be blank.\r\n",
      "check.quote.quantity.blank":     "- The quantity can not be blank.\r\n",
      "check.quote.quantity.invalid":   "- The quantity is not a valid number.\r\n",
      "check.quote.extendedprice.invalid":"The extended price is not a valid price.\n",
      "check.quote.estdeldate.invalid": "The estimated delivery date is not a valid date.\n",
      "check.quote.valid.entries":      "\nPlease enter valid entries to continue",
      "program.error.conditions":       "Programming Error. the location/module has to be specified for conditions",
      "program.error.remarks":          "Programming Error. the location/module has to be specified for remarks",
      "check.product.filename":         "Please specify the file to be uploaded",
      "image.thumbnail":                ": Thumbnail Image",
      "image.small":                    ": Small Image",
      "image.large":                    ": Large Image",
      "check.name":                     "- Please specify the  name\r\n",
      "label.all":                      "All",
      "check.number.invalid":           "- Please enter a valid Number\r\n",
      "check.product.quantity":         "Please provide atleast one product's quantity",
      "check.product.categoryName":     "- Please specify the category name\r\n",
      "check.product.code":             "- Code is required\r\n",
      "check.product.category":         "- Category is required\r\n",
      "verify.quote.newversion":        "Are you sure you want to create a new Version of this Quote?",
      "check.activity.folder.items":    "The activity folder form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n",
      "check.indentlevel.between":      "- Indent level must be between 0 and ",
      "check.number.loefield":          "- Only numbers are allowed in the LOE field\r\n",
      "user.cannotbechanged.reason":    "This user role cannot be changed.\r\nPossible reasons:\r\n- There must be at least one (1) other user with a project lead role\r\n- You must be in a role that can change user roles",
      "button.pleasewait":              "Please Wait...",
      "check.subject":                  "- Subject is a required field\r\n",
      "check.intro":                    "- Intro is a required field\r\n",
      "check.message":                  "The message could not be submitted.          \r\nPlease verify the following items:\r\n\r\n",
      "check.message.required":         "- Message is a required field\r\n",
      "check.forum.name":               "- Forum name is a required field\r\n",
      "select.product":                 "- Select a product\r\n",
      "quote.notmodifiable":            "The Quote can not be modified.\nPlease either Clone or Create a new Version.",
      "check.ticket.resolution.atclose":"- Resolution needs to be filled in when closing a ticket\r\n",
      "check.servicecontract.number":   "- Service Contract Number is required\r\n",
      "check.init.contract.date":       "- Initial Contract Date is required\r\n",
      "check.response.time":            "- Response Time is required\r\n",
      "check.telephone.service":        "- Telephone Service is required\r\n",
      "check.onsite.service":           "- Onsite Service is required\r\n",
      "check.email.service":            "- Email Service is required\r\n",
      "check.criteria":                 "- Criteria is required\r\n",
      "confirm.delete.folder":          "Are you sure you want to delete this folder and any associated data that may have been entered?",
      "confirm.delete.group":           "Are you sure you want to delete the group, the fields in this group, and any associated data entered in the module records?",
      "confirm.delete.field":           "Are you sure you want to delete the field, and any associated data entered in the module records?",
      "caution.nothingtoremove":        "Nothing to remove",
      "caution.itemneedstobe.selected": "An item needs to be selected before it can be removed",
      "caution.category.disabled":      "The selected category is already disabled",
      "button.addR":                    "Add >",
      "caution.category.exists":        "Category with that description already exists",
      "caution.doublequotes.notallowed":"Double Quotes are not allowed in the description",
      "caution.item.needed":            "An item needs to be selected",
      "caution.singleitem.needed":      "A single item needs to be selected",
      "caution.category.enabled":       "Category is already enabled",
      "button.updateR":                 "Update >",
      "check.username":                 "- Check that a Username is entered\r\n",
      "check.bothpasswords.match":      "- Check that both Passwords are entered correctly\r\n",
      "check.role.selected":            "- Check that a Role is selected\r\n",
      "alert.noentriestoactivate":      "No entries to activate",
      "confirm.looseChanges":           "You will lose all the changes made to the draft. Proceed ?",
      "check.campaign.name":            "- Campaign name is required\r\n",
      "option.none":                    "-- None --",
      "check.group.name":               "- Group Name is required\r\n",
      "check.campaign.criteria":        "Criteria could not be processed, please check the following:\r\n\r\n",
      "caution.selectfiletodownload":   "Please select a file to download",
      "caution.provideanswer.survey":   "Please Provide an answer for each survey item.\r\n\r\n",
      "label.activate":                 "Activate",
      "caution.provideanswer.required": "Please Provide an answer for all required survey items.\r\n\r\n",
      "check.reminder":                 "- Check that the reminder is entered correctly\r\n",
      "check.alertdate.beforetoday":    "Alert Date before today's date\r\n",
      "check.street.address":           "- Street Address is required\r\n",
      "check.city.name":                "- City Name is required\r\n",
      "check.state.name":               "- State Name is required\r\n",
      "check.country.name":             "- Country Name is required\r\n",
      "confirm.delete.contact.address": "Are you sure you want to delete the selected contact address?",
      "check.email.address":            "- Email Address is required\r\n",
      "check.valid.email.address":      "- Please enter a valid email address\r\n",
      "check.phonenumber":              "- Phone number is required\r\n",
      "check.valid.number":             "- Please enter a valid number\r\n",
      "alert.user.role.notchangable":   "This user role cannot be changed.\r\nPossible reasons:\r\n- There must be at least one (1) other user with a manager role\r\n- You must be in a role that can change user roles",
      "description.required.resubmit":  "Description is required, please verify then try submitting the information again.",
      "select.user.toreassign":         "- User to re-assign from must be selected",
      "alert.reassignments":            "Re-assignments could not be made, please check the following:\r\n\r\n",
      "select.hours":                   "- Hours is required\r\n",
      "check.hours.invalid":            "- Hours is invalid\r\n",
      "select.reason":                  "- Reason is required\r\n",
      "check.user.twologins.one":       "This application only permits a user to be logged in once\n and it appears that you are already logged in from\n the following internet address: ",
      "check.user.twologins.two":       ".\n\n This message could also appear if you did not previously log out\n and you are simply trying to login again from the same browser.\n\n Choose OK to continue logging in.\n Choose CANCEL to return to the login screen.",
      "specify.contact":                "- Please specify a contact\r\n",
      "check.length.wholenumber":       "- Check that Length is a whole number\r\n",
      "check.single.description":       "- Only a single description is allowed\r\n",
      "check.module":                   "Module is required\n",
      "check.targetdirectory":          "- Target directory is a required field\r\n",
      "check.firstname":                "- First name is a required field\r\n",
      "check.lastname":                 "- Last name is a required field\r\n",
      "check.company":                  "- Company is a required field\r\n",
      "check.company.blanks":           "- Please enter a valid Company name\r\n",
      "check.emailaddress":             "- Email address is a required field\r\n",
      "check.emailaddress.invalid":     "- Email address is invalid.  Make sure there are no invalid characters\r\n",
      "check.username":                 "- Username is a required field\r\n",
      "select.language":                "Please select a language to continue",
      "check.profile":                  "- Profile is a required field\r\n",
      "check.organization":             "- Organization is a required field\r\n",
      "check.proxyhost":                "- Proxy host is required field when proxy is checked\r\n",
      "check.proxyport.number":         "- Proxy port must be a number\r\n",
      "check.upgradesystem":            "Are you sure you want to upgrade the system now?",
      "check.password":                 "- Password is a required field\r\n",
      "check.licensekey":               "Enter the license key in the field to continue",
      "check.registration.option":      "Please select a registration option to continue",
      "status.change.requirement":      "Status can be changed only by the user who the task is assigned to",
      "cannot.delete.task.reason":      "Cannot delete the selected Task as it has been already assigned to another User.",
      "check.descriptionofservice":     "- Description of Service is required\r\n",
      "check.allitems.part.one":        "- Check that all items in row ",
      "check.allitems.part.two":        " are filled in\r\n",
      "check.issue.required":           "- Issue is required\r\n",
      "select.account.first":           "You have to select an Account first",
      "no.permission.addemployees":     "You do not have permission to add employees",
      "no.permission.addcontacts":      "You do not have permission to add contacts",
      "label.anyone":                   "Anyone",
      "check.alertdate":                "- Alert Date is mandatory if Follow-up Required is checked\r\n",
      "check.followup.description":     "- Follow-up Description is mandatory if Follow-up Required is checked\r\n",
      "alertdate.onlyif.followup":      "- Alert Date is required only if Follow-up Required is checked\r\n",
      "followupdesc.onlyif.followup":   "- Follow-up Description is required only if Follow-up Required is checked\r\n",
      "select.onerecipient":            "- Select at least one recipient\r\n",
      "check.subject":                  "- Enter a subject\r\n",
      "check.message":                  "- Enter a message in the body\r\n",
      "check.description":              "- Check that the description is entered\r\n",
      "select.one.emailaddress":        "- Select at least one email address\r\n",
      "specify.youremailaddress":       "- Please specify your email address\r\n",
      "select.organization.and.contact":"Please select an Organization and a Contact",
      "select.organization.first":      "Please select an organization first",
      "confirm.delete.item":            "Are you sure you want to remove this item?",
      "quote.submitted.withoutaction":  "Quote submitted without performing any action.",
      "no.adjustment":                  "No adjustment",
      "label.none":                     "None",
      "label.show":                     "Show",
      "label.hide":                     "Hide",
      "label.any":                      "Any",
      "specify.contact":                "- Please specify a contact\r\n",
      "webcal.message":                 "Viewing your Concourse Suite Community Edition calendar offline requires an iCalendar compliant desktop application. These applications include Mozilla Sunbird, Apple iCal, and possibly others.   \r\n\r\nThis feature may not work on your system.",
      "cannot.assign":                  "The lead could not be assigned. Please skip the current Lead",
      "confirm.delete.lead":            "Are you sure you want to delete this lead?",
      "check.postalcode":               "- Please enter a valid Postal Code\r\n",
      "check.phone.ext":                "- Please enter a valid phone number extension\r\n",
      "provide.optionvalue":            "You must provide a value for the new option",
      "label.entryalreadyexists":       "Entry already exists",
      "label.nothingtorename":          "Nothing to rename",
      "label.contact.lastName":          "Last Name",
      "label.contact.firstName":          "First Name",
      "label.account.name":              "Account Name",
      "label.account.number":          "Account Number",
      "label.account.sc.serialnumber":              "Serial Number",
      "label.account.sc.number":          "Service Contract Number",
      "select.torename":                "An item needs to be selected before it can be renamed",
      "required.oneiteminlist":         "You must have at least one item in this list.",
      "check.textmessage":              "- At least one entered text message address is invalid.  Make sure there are no invalid characters\r\n",
      "confirm.quotesubmit":            "Are you sure you want to submit this quote for review by the selected contact?",
      "confirm.quoteproductclone":      "Are you sure you want to clone the selected quote item?",
      "label.news.required":            "- Check that the news label is entered\r\n",
      "label.discussion.required":            "- Check that the discussion label is entered\r\n",
      "label.documents.required":            "- Check that the documents label is entered\r\n",
      "label.lists.required":            "- Check that the lists label is entered\r\n",
      "label.plan.required":            "- Check that the plan label is entered\r\n",
      "label.tickets.required":            "- Check that the tickets label is entered\r\n",
      "label.accounts.required":            "- Check that the accounts label is entered\r\n",
      "label.team.required":            "- Check that the team label is entered\r\n",
      "label.details.required":            "- Check that the details label is entered\r\n",
      "check.valid.input":              "Please enter a valid input",
      "check.valid.date":               "Please enter a valid date",
      "check.avoided.text":             "Please enter a valid input. Avoid [*^|]",
      "my.opportunities":               "My Opportunities",
      "check.valid.manager":            "- Please select a valid user to manage the opportunity\r\n",
      "check.valid.owner":              "- Please select a valid user to assign the component\r\n",
      "check.clientName":               "- Name is a required field\r\n",
      "check.clientCode":               "- Code is a required field\r\n",
      "actionstep.update.alert":      "You cannot update the status of this step at this time",
      "actionstep.rating.alert":       "This step requires a valid rating selection",
      "actionstep.relationship.alert":   "This step requires you to add a relationship",
      "actionstep.attachment.alert":    "This step requires an attachment before it can be completed",
      "label.undetermined":             "Undetermined",
      "check.ticket.assignToUser":      "- Please assign the ticket to create an action plan.\r\n",
      "check.set.actionPlan":           "- Please select a category with an action plan to activate the plan.\r\n",
      "check.actionplan":               "- Please select an action plan to be inserted.\r\n",
      "site.required":                  "- Site is a required field\r\n",
      "ticket.selectOrganizationFirst": "An organization needs to be selected first",
      "lead.selectSiteFirst":           "A site needs to be selected first",
      "label.sendEmail":                "Send Email",
      "check.name.last.blanks":         "Please enter a valid last name.\r\n",
      "check.oneitem.selected":         "Please select at least one item to proceed.",
      "check.valid.description":        "- Please enter a valid description.\r\n",
      "check.valid.caption":            "- Please enter a valid caption.\r\n",
      "check.valid.name":               "- Please enter a valid name.\r\n",
      "confirm.restart.campaign":       "Are you sure you want to restart this campaign?",
      "check.campaign.selection":       "- Please select a valid active campaign.\r\n",
      "check.material.quantity.one":    "- Please enter a valid Number for ",
      "check.products.import.name.long":  "- Import name is too long\r\n",
      "label.sending":                  "Sending...",
      "check.companylastname.blanks":	  "- Please enter a valid company name or last name.\r\n",
      "folderselcted.range.notzero":    "- Range should not be zero or not entered\r\n"
    }
  }
}
