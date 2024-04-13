import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { UserState } from '../../../shared/user.state';

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
      confirmSave: true,
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

  constructor(private userService: UserAPI, private store: Store) { }

  ngOnInit(): void {
    this.getUserProjects();
  }

  getUserProjects(): void {
    const userId = this.store.selectSnapshot(UserState.getUserId)

    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        this.projectsData = userData.data.projects
        console.log(this.projectsData)
        this.mapProjectsToTable();
      },
      (error: any) => {
        console.error('Error fetching user education:', error);
      }
    )
  }

  mapProjectsToTable(): void {
    this.source.load(this.projectsData);
  }

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to save the changes?')) {
      const projectId = event.data.id;
      const data = event.newData;

      if (Object.values(data).some(value => !value)) {
        window.alert('Please fill in all fields before saving.');
        event.confirm.reject();
        return;
      }

      this.userService.updateProject(projectId, data).subscribe(
        (response: any) => {
          console.log('Project updated successfully:', response);
          event.confirm.resolve();
        },
        (error: any) => {
          console.error('Error updating project:', error);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }


  onDeleteConfirm(event): void {
    if (this.projectsData.length === 1) {
      window.alert('You cannot delete the last item.');
      event.confirm.reject();
      return;
    }

    if (window.confirm('Are you sure you want to delete?')) {
      const projectId = event.data.id;
      this.userService.deleteProject(projectId).subscribe(
        () => {
          const index = this.projectsData.findIndex(item => item.id === projectId);
          if (index !== -1) {
            this.projectsData.splice(index, 1);
            this.source.load(this.projectsData);
          }
          event.confirm.resolve();
        },
        (error) => {
          console.error('Error deleting project:', error);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }

}
