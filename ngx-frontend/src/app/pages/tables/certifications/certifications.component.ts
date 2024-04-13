import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { UserAPI } from '../../../service/api/user-api.service';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'ngx-certifications',
  templateUrl: './certifications.component.html',
  styleUrls: ['./certifications.component.scss']
})
export class CertificationsComponent implements OnInit {
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
      startDate: {
        title: 'Start Date',
        type: 'string',
      },
      endDate: {
        title: 'End Date',
        type: 'string',
      },
      issuer: {
        title: 'Issuer',
        type: 'string',
      },
      description: {
        title: 'Description',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();
  certificationsData: any[] = [];

  constructor(private userService: UserAPI, private authService: AuthService) { }

  ngOnInit(): void {
    this.getUserCertifications();
  }

  getUserCertifications(): void {
    const userId = '6607446b04d3bb099d1bc4dc'; // Replace 'userId' with the actual user ID
    this.userService.getUserDetails(userId).subscribe(
      (userData: any) => {
        console.log(userData); // Log the fetched user data to the console
        this.certificationsData = userData.data.certificates; // Assign certifications data to class variable
        this.mapCertificationsToTable(); // Map certifications data to table columns
      },
      (error: any) => {
        console.error('Error fetching user certifications:', error);
      }
    );
  }

  mapCertificationsToTable(): void {
    this.source.load(this.certificationsData);
  }

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to save the changes?')) {
      const certificationId = event.data.id; // Get the certification ID from the event data
      const data = event.newData;
      // Check if any field is empty before updating
      if (Object.values(data).every(value => !!value)) {
        this.userService.updateCertification(certificationId, data).subscribe(
          (response: any) => {
            console.log('Certification updated successfully:', response);
            event.confirm.resolve(); // Resolve the edit event
          },
          (error: any) => {
            console.error('Error updating certification:', error);
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

    if (this.certificationsData.length === 1) {
      window.alert('You cannot delete the last item.');
      event.confirm.reject();
      return;
    }

    if (window.confirm('Are you sure you want to delete?')) {
      const certificationId = event.data.id;
      this.userService.deleteCertification(certificationId).subscribe(
        () => {
          const index = this.certificationsData.findIndex(item => item.id === certificationId);
          if (index !== -1) {
            this.certificationsData.splice(index, 1);
            this.source.load(this.certificationsData);
          }
          event.confirm.resolve();
        },
        (error) => {
          console.error('Error deleting certification:', error);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }

}
