import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { AuthService } from '../../../service/auth.service';

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

  constructor(private userService: UserAPI, private authService: AuthService) { }

  ngOnInit(): void {
    this.getUserEducation();
  }

  getUserEducation(): void {
    const userId = '6607446b04d3bb099d1bc4dc'; // Replace 'userId' with the actual user ID
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
      const userId = '66073ef404d3bb099d1bc4d3'; // Replace 'userId' with the actual user ID
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
      event.confirm.reject(); // Reject the edit event
    }
  }

  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }
}
