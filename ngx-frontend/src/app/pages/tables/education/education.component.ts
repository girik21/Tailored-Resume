import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { AuthService } from '../../../service/auth.service';
import { UserState } from '../../../shared/user.state';

@Component({
  selector: 'ngx-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent implements OnInit {
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
      degreeName: {
        title: 'Degree Name',
        type: 'string',
      },
      major: {
        title: 'Major',
        type: 'string',
      },
      minor: {
        title: 'Minor',
        type: 'string',
      },
      school: {
        title: 'School',
        type: 'string',
      },
      location: {
        title: 'Location',
        type: 'string',
      },
      startDate: {
        title: 'Start Date',
        type: 'string',
      },
      endDate: {
        title: 'End Date',
        type: 'string',
      },
      graduated: {
        title: 'Graduated',
        type: 'boolean',
      },
      grade: {
        title: 'Grade',
        type: 'number',
      },
      description: {
        title: 'Description',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();
  educationData: any[] = [];

  constructor(private userService: UserAPI, private authService: AuthService, private store: Store) { }

  ngOnInit(): void {
    this.getUserEducation();
  }

  getUserEducation(): void {
    const userId = this.store.selectSnapshot(UserState.getUserId);
    if (!userId) {
      console.error('User Id not found')
      return;
    }

    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        console.log(userData); // Log the fetched user data to the console
        this.educationData = userData.data.education; // Assign education data to class variable
        this.mapEducationToTable(); // Map education data to table columns
      },
      (error: any) => {
        console.error('Error fetching user education:', error);
      }
    );
  }

  mapEducationToTable(): void {
    this.source.load(this.educationData);
  }

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to save the changes?')) {
      // Retrieve userId from the state
      const userId = this.store.selectSnapshot(UserState.getUserId); // Get userId from the state
      if (!userId) {
        console.error('User ID not found in the state.');
        return;
      }

      const data = event.newData;
      this.userService.saveEducation(data, userId).subscribe(
        (response: any) => {
          console.log('Education saved successfully:', response);
          event.confirm.resolve(); // Resolve the edit event
        },
        (error: any) => {
          console.error('Error saving education:', error);
          event.confirm.reject(); // Reject the edit event
        }
      );
    } else {
      event.confirm.reject();
    }
  }

  onDeleteConfirm(event): void {
    if (this.educationData.length === 1) {
      window.alert('You cannot delete the last item.');
      event.confirm.reject();
      return;
    }

    if (window.confirm('Are you sure you want to delete?')) {
      const educationId = event.data.id;
      this.userService.deleteEducation(educationId).subscribe(
        () => {
          const index = this.educationData.findIndex(item => item.id === educationId);
          if (index !== -1) {
            this.educationData.splice(index, 1);
            this.source.load(this.educationData);
          }
          event.confirm.resolve();
        },
        (error) => {
          console.error('Error deleting education:', error);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }

}
