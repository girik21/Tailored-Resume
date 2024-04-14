import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { UserState } from '../../../shared/user.state';

@Component({
  selector: 'ngx-experiences',
  templateUrl: './experiences.component.html',
  styleUrls: ['./experiences.component.scss']
})
export class ExperiencesComponent implements OnInit {
  settings = {
    // Define your table settings here
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-edit"></i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
      confirmSave: true,
    },
    delete: {
      deleteButtonContent: '<i class="nb-trash"></i>',
      confirmDelete: true,
    },
    columns: {
      position: {
        title: 'Position',
        type: 'string',
      },
      employer: {
        title: 'Employer',
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
      currentJob: {
        title: 'Current Job',
        type: 'string',
      },
      description: {
        title: 'Description',
        type: 'string',
      },
      companyLink: {
        title: 'Company Link',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();
  experiencesData: any[] = [];

  constructor(private userService: UserAPI, private store: Store) { }

  ngOnInit(): void {
    this.getUserExperiences();
  }

  getUserExperiences(): void {
    const userId = this.store.selectSnapshot(UserState.getUserId)

    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        this.experiencesData = userData.data.experiences;
        console.log(userData)
        this.mapExperiencesToTable();
      },
      (error: any) => {
        console.error('Error fetching user education:', error);
      }
    )
  }

  mapExperiencesToTable(): void {
    this.source.load(this.experiencesData);
  }

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to save the changes?')) {
      const experienceId = event.data.id; // Get the experience ID from the event data
      const data = event.newData;
      // Check if any field is empty before updating
      if (Object.values(data).every(value => !!value)) {
        this.userService.updateExperience(experienceId, data).subscribe(
          (response: any) => {
            console.log('Experience updated successfully:', response);
            event.confirm.resolve(); // Resolve the edit event
          },
          (error: any) => {
            console.error('Error updating experience:', error);
            event.confirm.reject(); // Reject the edit event
          }
        );
      } else {
        window.alert('Please fill in all fields before saving.');
        event.confirm.reject(); // Reject the edit event
      }
    } else {
      event.confirm.reject(); // Reject the edit event
    }
  }


  onDeleteConfirm(event): void {
    if (this.experiencesData.length === 1) {
      window.alert('You cannot delete the last item.');
      event.confirm.reject();
      return;
    }

    if (window.confirm('Are you sure you want to delete?')) {
      const experienceId = event.data.id;
      this.userService.deleteExperience(experienceId).subscribe(
        () => {
          const index = this.experiencesData.findIndex(item => item.id === experienceId);
          if (index !== -1) {
            this.experiencesData.splice(index, 1);
            this.source.load(this.experiencesData);
          }
          event.confirm.resolve();
        },
        (error) => {
          console.error('Error deleting experience:', error);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }


}
