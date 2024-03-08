import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';

@Component({
  selector: 'app-pdf-maker',
  templateUrl: './pdf-maker.component.html',
  styleUrls: ['./pdf-maker.component.css']
})
export class PdfMakerComponent {
  jobDescription: string = '';
  responseData: any;

  constructor(private http: HttpClient) {
    (window as any).pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePDF() {
    if (this.jobDescription.trim() !== '') {
      const userToken = localStorage.getItem('accessToken'); // Get the user token from localStorage
      if (!userToken) {
        console.error('User token not found.');
        return;
      }

      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + userToken,
        'Content-Type': 'application/json'
      });

      this.http.post<any>('http://localhost:8080/api/openai/chat/65e505389eb0d0350c385d1b', { jobDesc: this.jobDescription }, { headers })
        .subscribe(response => {
          this.responseData = response.data;
          const documentDefinition = this.createDocumentDefinition(this.responseData);
          pdfMake.createPdf(documentDefinition).open();
        }, error => {
          console.error('Error occurred:', error);
        });
    } else {
      console.error('Job description is required.');
    }
  }

  createDocumentDefinition(data: any): any {
    return {
      content: [
        { text: data.name, style: 'header', alignment: 'center' },
        { text: 'Contact Information:', style: 'subheader' },
        {
          columns: [
            { text: 'LinkedIn: ' + (data.contact.LinkedIn || 'Not Provided') + '\nPhone: ' + data.contact.phone + '\nLocation: ' + data.contact.location },
            { text: 'Portfolio: ' + (data.contact.portfolio || 'Not Provided') + '\nEmail: ' + data.contact.email + '\nGitHub: ' + (data.contact.github || 'Not Provided') }
          ]
        },
        { text: 'Skills:', style: 'subheader' },
        {
          ul: data.skills.programmingLanguages.map((lang: string) => ({ text: lang })),
        },
        { text: 'Databases:', style: 'subheader' },
        {
          ul: data.skills.databases.map((db: string) => ({ text: db })),
        },
        { text: 'Frameworks:', style: 'subheader' },
        {
          ul: data.skills.frameworks.map((framework: string) => ({ text: framework })),
        },
        { text: 'Other Technologies:', style: 'subheader' },
        {
          ul: data.skills.otherTechnologies.map((tech: string) => ({ text: tech })),
        },
        { text: 'Cloud Platforms:', style: 'subheader' },
        {
          ul: data.skills.cloudPlatforms.map((platform: string) => ({ text: platform })),
        },
        { text: 'Development Practices:', style: 'subheader' },
        {
          ul: data.skills.developmentPractices.map((practice: string) => ({ text: practice })),
        },
        { text: 'Work History:', style: 'subheader' },
        ...data.workHistory.map((entry: any) => {
          return [
            {
              columns: [
                { text: entry.company + ', ' + entry.location + ', ' + entry.from + ' - ' + entry.to, bold: true },
                { text: entry.position, bold: true }
              ]
            },
            { text: entry.responsibilities }
          ];
        }),
        { text: 'Education:', style: 'subheader' },
        ...data.education.map((edu: any) => {
          return [
            { text: edu.degree + ' in ' + edu.school + ', ' + edu.from + ' - ' + edu.to, bold: true }
          ];
        }),
        { text: 'Projects:', style: 'subheader' },
        ...data.projects.map((project: any) => {
          return [
            { text: project.name, style: 'projectName' },
            { text: project.description, style: 'projectDescription' },
            { text: 'Date: ' + project.date, style: 'projectDate' }
          ];
        }).flat(),
        { text: 'Mentorship:', style: 'subheader' },
        {
          ul: [
            'Role: ' + (data.mentorship.role || 'Not Provided'),
            'Responsibilities: ' + (data.mentorship.responsibilities || 'Not Provided')
          ]
        }
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true
        },
        subheader: {
          fontSize: 14,
          bold: true,
          margin: [0, 10, 0, 5]
        },
        projectName: {
          fontSize: 14,
          bold: true,
          margin: [0, 15, 0, 0]
        },
        projectDescription: {
          fontSize: 12,
          margin: [0, 0, 0, 5]
        },
        projectDate: {
          fontSize: 10,
          italic: true,
          margin: [0, 0, 0, 10]
        }
      }
    };
  }
}
