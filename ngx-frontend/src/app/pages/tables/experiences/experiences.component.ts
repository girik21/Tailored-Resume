import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { AuthService } from '../../../service/auth.service';

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

  constructor(private userService: UserAPI, private authService: AuthService) { }

  ngOnInit(): void {
    this.getUserExperiences();
  }

  getUserExperiences(): void {
    const userId = '66073ef404d3bb099d1bc4d3'; // Replace 'userId' with the actual user ID
    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        console.log(userData); // Log the fetched user data to the console
        this.experiencesData = userData.data.experiences; // Assign experiences data to class variable
        this.mapExperiencesToTable(); // Map experiences data to table columns
      },
      (error: any) => {
        console.error('Error fetching user experiences:', error);
      }
    );
  }

  mapExperiencesToTable(): void {
    this.source.load(this.experiencesData);
  }

  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }
}
