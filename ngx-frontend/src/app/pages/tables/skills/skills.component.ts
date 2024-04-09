import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { UserState } from '../../../shared/user.state';

@Component({
  selector: 'ngx-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent implements OnInit {
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
      }
    },
  };

  source: LocalDataSource = new LocalDataSource();
  skillsData: any[] = [];

  constructor(private userService: UserAPI, private store: Store) { }

  ngOnInit(): void {
    this.getUserSkills();
  }

  getUserSkills(): void {
    const userId = this.store.selectSnapshot(UserState.getUserId)

    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        this.skillsData = userData.data.skills
        this.mapSkillsToTable();
      }
    )
  }

  mapSkillsToTable(): void {
    this.source.load(this.skillsData);
  }

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to save the changes?')) {
      const userId = '66084a049eb7556f98c69050'; //
      const data = event.newData;
      this.userService.saveSkills(data, userId).subscribe(
        (response: any) => {
          console.log('Skills saved successfully:', response);
          event.confirm.resolve();
        },
        (error: any) => {
          console.error('Error saving skills:', error);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
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
