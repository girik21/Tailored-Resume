import { Component } from '@angular/core';
import { Store } from '@ngxs/store';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { AuthService } from '../../../service/auth.service';
import { UserState } from '../../../shared/user.state';

@Component({
  selector: 'ngx-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent {


  settings = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-edit"></i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
      deleteButtonContent: '<i class="nb-trash"></i>',
      confirmDelete: true,
    },
    columns: {
      username: {
        title: 'Username',
        type: 'string',
      },
      email: {
        title: 'E-mail',
        type: 'string',
      },
      phone: {
        title: 'Phone Number',
        type: 'string',
      },
      address1: {
        title: 'Street Address',
        type: 'string',
      },
      address2: {
        title: 'Address 2',
        type: 'string',
      },
      city: {
        title: 'City',
        type: 'string',
      },
      state: {
        title: 'State',
        type: 'string',
      },
      zip: {
        title: 'Zip',
        type: 'string',
      },
      linkedinLink: {
        title: 'LinkedIn URL',
        type: 'string',
      },
      portfolioLink: {
        title: 'Portfolio Link / Website',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();
  userData: any;

  constructor(private userService: UserAPI, private authService: AuthService, private store: Store) { }

  ngOnInit(): void {
    // Get user details and load data into the table
    this.getUserDetails();
  }

  getUserDetails(): void {
    // Retrieve userId from the state
    const userId = this.store.selectSnapshot(UserState.getUserId); // Get userId from the state
    if (!userId) {
      console.error('User ID not found in the state.');
      return;
    }

    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        console.log(userData); // Log the fetched user data to the console
        this.userData = userData; // Assign userData to class variable
        this.mapUserDataToTable(); // Map user data to table columns
      },
      (error: any) => {
        console.error('Error fetching user details:', error);
      }
    );
  }

  mapUserDataToTable(): void {
    const data = [{
      username: this.userData.data.username,
      email: this.userData.data.email,
      phone: this.userData.data.phone,
      address1: this.userData.data.address1,
      address2: this.userData.data.address2,
      city: this.userData.data.city,
      state: this.userData.data.state,
      zip: this.userData.data.zip,
      linkedinLink: this.userData.data.linkedinLink,
      portfolioLink: this.userData.data.portfolioLink,
    }];
    this.source.load(data); // Load data into the table
  }

  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }
}
