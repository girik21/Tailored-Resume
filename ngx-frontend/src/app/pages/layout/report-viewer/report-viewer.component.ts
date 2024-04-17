import { Component, Input, ViewChild, ElementRef, Renderer2 } from '@angular/core';
//import jsPDF from 'jspdf';


import { Packer } from "docx";
import { saveAs } from "file-saver";


import { achievements, education, experiences, skills } from "./cv-data";
import { DocumentCreator } from "./cv-generator";


// //////       pdf =================

import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
pdfMake.vfs = pdfFonts.pdfMake.vfs;
//========================================================


@Component({
  selector: 'app-report-viewer',
  templateUrl: './report-viewer.component.html',
  styleUrls: ['./report-viewer.component.scss']
})
export class ReportViewerComponent {
  @Input() reportFields: any[] = [];
  @ViewChild('reportContent', { static: false }) reportContent: ElementRef;
  @Input() formData: any;

  full_name: string = '';
  fontSize = 16;
  fontMenuOpen = false;



  selectedValue = '1';

  fontTypes: string[] = [
    'Arial', 'Times New Roman', 'Verdana', 'Tahoma', 'Courier New', 'Georgia', 'Palatino Linotype',
    'Impact', 'Comic Sans MS', 'Arial Black', 'Arial Narrow', 'Bookman Old Style', 'Century Gothic',
    'Garamond', 'Franklin Gothic Medium', 'Optima', 'Segoe UI', 'Lucida Console', 'Lucida Sans Unicode',
    'Trebuchet MS', 'Arial Rounded MT Bold', 'Consolas', 'Candara', 'Constantia', 'Corbel',
    'Franklin Gothic Book', 'Marlett', 'MS Reference Sans Serif', 'MS Reference Specialty', 'MS Sans Serif',
    'MS Serif', 'Myanmar Text', 'Palace Script MT', 'Perpetua', 'Rockwell', 'Showcard Gothic',
    'Sitka', 'Symbol', 'Webdings', 'Wide Latin', 'Wingdings', 'Wingdings 2', 'Wingdings 3'
  ];

  fontOptions = [
    { value: 'Arial', label: 'Arial' },    { value: 'Verdana', label: 'Verdana' },    { value: 'Helvetica', label: 'Helvetica' },    { value: 'Times New Roman', label: 'Times New Roman' },    { value: 'Calibri', label: 'Calibri' },    { value: 'Georgia', label: 'Georgia' },    { value: 'Garamond', label: 'Garamond' },    { value: 'Cambria', label: 'Cambria' },    { value: 'Lato', label: 'Lato' },    { value: 'Roboto', label: 'Roboto' },    { value: 'Open Sans', label: 'Open Sans' },    { value: 'Trebuchet MS', label: 'Trebuchet MS' },
    { value: 'Palatino Linotype', label: 'Palatino Linotype' }
    // Add more fonts as needed
  ];

  selectedFont = 'Times New Roman';

  constructor(private renderer: Renderer2) { }

  ngAfterViewInit() {
    // Set the default font for the report content after view initialization
    this.setDefaultFont();
  }

  setDefaultFont() {
    // Specify the default font here
    const defaultFont = 'Palatino Linotype';

    // Apply the default font to the report content
    this.changeFontType(defaultFont);
  }
  increaseFontSize() {

    this.fontSize += 2;
    this.updateFontSize();
  }

  decreaseFontSize() {
    if (this.fontSize > 2) {
      this.fontSize -= 2;
      this.updateFontSize();
    }
  }

  updateFontSize() {
    const elements = this.reportContent.nativeElement.querySelectorAll('*');
    elements.forEach((element: HTMLElement) => {
      element.style.fontSize = this.fontSize + 'px';
    });
  }

  changeFontType(font: string) {
    const elements = this.reportContent.nativeElement.querySelectorAll('*');
    elements.forEach((element: HTMLElement) => {
      element.style.fontFamily = font;
    });
  }




  toggleFontMenu() {
    this.fontMenuOpen = !this.fontMenuOpen;
  }
  downloadAsPDF() {


    // const doc = new jsPDF();
    // const content = this.reportContent.nativeElement.innerHTML;
    // const tempElement = document.createElement('div');
    // tempElement.style.visibility = 'hidden';
    // tempElement.style.position = 'absolute';
    // tempElement.innerHTML = content;
    // document.body.appendChild(tempElement);

    // const h2Elements = tempElement.querySelectorAll('h2');
    // h2Elements.forEach((h2Element: HTMLElement) => {
    //   const hrElement = document.createElement('hr');
    //   h2Element.appendChild(hrElement);
    // });

    // doc.setFontSize(this.fontSize);

    // doc.fromHTML(tempElement, 10, 10, {}, () => {
    //   doc.save('report.pdf');
    //   document.body.removeChild(tempElement);
    // });
  }


  downloadAsWord() {
    const documentCreator = new DocumentCreator();

    const contactInfo = {
      NAME: this.formData["Personal Details"].username,
      PHONE_NUMBER: this.formData["Personal Details"].phone,
      PROFILE_URL: this.formData["Personal Details"].linkedinLink,
      EMAIL: this.formData["Personal Details"].email,
      ADDRESS: `${this.formData["Personal Details"].address1}, ${this.formData["Personal Details"].city}, ${this.formData["Personal Details"].state} ${this.formData["Personal Details"].zip}`
    };

    // Iterate through formData.Experience and transform each experience
    const transformedExperiences = this.formData.Experience.map(this.transformExperience);

    const transformedEducations = this.formData.Education.map(this.transformEducation);
   
    const transformedSkills = this.formData.Skills;
   

   const transformedProjects = this.formData.Project.map(this.transformProject);

   console.log(this.formData["Professional Summary"].professionalSummary)
   const professionalSummary = {
    description: this.formData["Professional Summary"].professionalSummary
      };

    const doc = documentCreator.create([
      contactInfo,
      transformedExperiences,//experiences,
      transformedEducations,//education,
      transformedSkills,//skills,
      transformedProjects,// projects,
      // certifications,
       professionalSummary
    ]);

    Packer.toBlob(doc).then(blob => {
      console.log(blob);
      saveAs(blob, "resue-tailor.docx");
      console.log("Document created successfully");
    });
  }



  formatSkills(): string {
    if (this.formData && this.formData.Skills) {
      return this.formData.Skills.map(skill => skill.name).join(', ');
    }
    return '';
  }

  // Function to transform dynamic experience to match the structure of static experience
  transformExperience(dynamicExperience: Experience) {
    // Parse start date string into a Date object
    const startDate = dynamicExperience.startDate ? new Date(dynamicExperience.startDate) : null;

    // Extract month and year from the start date
    const startMonth = startDate ? startDate.getMonth() + 1 : 1; // Adding 1 because getMonth() returns zero-based month index
    const startYear = startDate ? startDate.getFullYear() : 2000; // Default year if startDate is null

    // Parse end date string into a Date object
    const endDate = dynamicExperience.endDate ? new Date(dynamicExperience.endDate) : null;

    // Extract month and year from the end date
    const endMonth = endDate ? endDate.getMonth() + 1 : 1; // Adding 1 because getMonth() returns zero-based month index
    const endYear = endDate ? endDate.getFullYear() : 2000; // Default year if endDate is null

    return {
      isCurrent: false, // Assuming all experiences in formData are historical, not current
      summary: dynamicExperience.description,
      title: dynamicExperience.position,
      startDate: {
        month: startMonth,
        year: startYear
      },
      endDate: {
        month: endMonth,
        year: endYear
      },
      company: {
        name: dynamicExperience.employer
      }
    };
  }

  // Function to transform dynamic experience to match the structure of static experience
  transformEducation(dynamicEducation) {
    // Parse start date string into a Date object
    const startDate = dynamicEducation.startDate ? new Date(dynamicEducation.startDate) : null;

    // Extract month and year from the start date
    const startMonth = startDate ? startDate.getMonth() + 1 : 1; // Adding 1 because getMonth() returns zero-based month index
    const startYear = startDate ? startDate.getFullYear() : 2000; // Default year if startDate is null

    // Parse end date string into a Date object
    const endDate = dynamicEducation.endDate ? new Date(dynamicEducation.endDate) : null;

    // Extract month and year from the end date
    const endMonth = endDate ? endDate.getMonth() + 1 : 1; // Adding 1 because getMonth() returns zero-based month index
    const endYear = endDate ? endDate.getFullYear() : 2000; // Default year if endDate is null
   
    return {

      fieldOfStudy: dynamicEducation.degreeName, // Assuming fieldOfStudy is not provided in formData
      notes: dynamicEducation.description,
      schoolName: dynamicEducation.school,
      startDate: {
        year: startYear
      },
      endDate: {
        year: endYear
      }
    };
  }

  // Function to transform dynamic project to match the structure of static project
transformProject(dynamicProject) {
  
  return {
      title: dynamicProject.name,
      startDate: dynamicProject.startDate,
      endDate: dynamicProject.endDate,
      company: dynamicProject.employer 
      };
}

}



interface Experience {
  employer: string;
  startDate: string;
  endDate: string;
  position: string;
  description: string;
}

