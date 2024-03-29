import { Component } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import jsonData from './experience.json';

@Component({
  selector: 'app-pdf-maker',
  templateUrl: './pdf-maker.component.html',
  styleUrls: ['./pdf-maker.component.css']
})
export class PdfMakerComponent {
  jsonData: any = jsonData;

  constructor() {
    (window as any).pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePDF() {
    const documentDefinition = this.createDocumentDefinition(this.jsonData);
    pdfMake.createPdf(documentDefinition).open();
  }

  createDocumentDefinition(data: any): any {
    return {
      content: [
        { text: data.name, style: 'header', alignment: 'center' },
        { text: 'Contact Information:', style: 'subheader' },
        {
          columns: [
            { text: 'LinkedIn: ' + data.contact.LinkedIn + '\nPhone: ' + data.contact.phone + '\nLocation: ' + data.contact.location },
            { text: 'Portfolio: ' + data.contact.portfolio + '\nEmail: ' + data.contact.email + '\nGitHub: ' + data.contact.github }
          ]
        },
        { text: 'Skills:', style: 'subheader' },
        {
          ul: [
            { text: 'Programming Languages: ' + data.skills.programmingLanguages.join(', ') },
            { text: 'Databases: ' + data.skills.databases.join(', ') },
            { text: 'Frameworks: ' + data.skills.frameworks.join(', ') },
            { text: 'Other Technologies: ' + data.skills.otherTechnologies.join(', ') },
            { text: 'Cloud Platforms: ' + data.skills.cloudPlatforms.join(', ') },
            { text: 'Development Practices: ' + data.skills.developmentPractices.join(', ') },
            { text: 'Proficiency: ' + data.skills.proficiency.join(', ') }
          ]
        },
        { text: 'Work History:', style: 'subheader' },
        ...data.workHistory.flatMap((entry: any, index: number, array: any[]) => [
          {
            ul: [
              { text: entry.position + ' at ' + entry.company + ', ' + entry.location + ', ' + entry.dates, bold: true },
              { ul: entry.responsibilities }
            ]
          },
          index !== array.length - 1 ? { text: '\n' } : null // Add space between different companies
        ]),
        { text: 'Education:', style: 'subheader' },
        {
          ul: [
            data.education.degree + ' in ' + data.education.major + ' from ' + data.education.institution + ', ' + data.education.location + ', ' + data.education.dates
          ]
        },
        { text: 'Projects:', style: 'subheader' },
        ...data.projects.flatMap((project: any, index: number, array: any[]) => [
          { text: project.name, style: 'projectName' },
          { text: project.description, style: 'projectDescription' },
          { text: 'Date: ' + project.date, style: 'projectDate' },
          index !== array.length - 1 ? { text: '\n' } : null // Add space between different projects
        ]),
        { text: 'Mentorship:', style: 'subheader' },
        {
          ul: [
            'Role: ' + data.mentorship.role,
            'Responsibilities: ' + data.mentorship.responsibilities
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
