import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'ngx-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
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
      name: {
        title: 'Name',
        type: 'string',
      },
      link: {
        title: 'Link',
        type: 'string',
      },
      description: {
        title: 'Description',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();
  projectsData: any[] = [];

  constructor(private userService: UserAPI, private authService: AuthService) { }

  ngOnInit(): void {
    this.getUserProjects();
  }

  getUserProjects(): void {
    const userId = '66073ef404d3bb099d1bc4d3'; // Replace 'userId' with the actual user ID
    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        console.log(userData); // Log the fetched user data to the console
        this.projectsData = userData.data.projects; // Assign projects data to class variable
        this.mapProjectsToTable(); // Map projects data to table columns
      },
      (error: any) => {
        console.error('Error fetching user projects:', error);
      }
    );
  }

  mapProjectsToTable(): void {
    this.source.load(this.projectsData);
  }

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to save the changes?')) {
      const userId = '66073ef404d3bb099d1bc4d3'; // Replace 'userId' with the actual user ID
      const data = event.newData;
      this.userService.saveProjects(data, userId).subscribe(
        (response: any) => {
          console.log('Projects saved successfully:', response);
          event.confirm.resolve(); // Resolve the edit event
        },
        (error: any) => {
          console.error('Error saving projects:', error);
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
