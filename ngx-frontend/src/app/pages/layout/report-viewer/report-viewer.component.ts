import { Component, ElementRef, Input, Renderer2, ViewChild } from '@angular/core';
import jsPDF from 'jspdf';

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


  items = [
    { value: '1', label: 'Option 1' },
    { value: '2', label: 'Option 2' },
    // Add more items as needed
  ];
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
    { value: 'Arial', label: 'Arial' },
    { value: 'Verdana', label: 'Verdana' },
    { value: 'Helvetica', label: 'Helvetica' },
    { value: 'Times New Roman', label: 'Times New Roman' },
    { value: 'Calibri', label: 'Calibri' },
    { value: 'Georgia', label: 'Georgia' },
    { value: 'Garamond', label: 'Garamond' },
    { value: 'Cambria', label: 'Cambria' },
    { value: 'Lato', label: 'Lato' },
    { value: 'Roboto', label: 'Roboto' },
    { value: 'Open Sans', label: 'Open Sans' },
    { value: 'Trebuchet MS', label: 'Trebuchet MS' },
    { value: 'Palatino Linotype', label: 'Palatino Linotype' }
    // Add more fonts as needed
  ];

  selectedFont = 'Arial';

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
    const elements = this.reportContent.nativeElement.querySelectorAll('p, h6');
    elements.forEach((element: HTMLElement) => {
      element.style.fontSize = this.fontSize + 'px';
    });
  }

  changeFontType(font: string) {
    const elements = this.reportContent.nativeElement.querySelectorAll('p, h6, headers');
    elements.forEach((element: HTMLElement) => {
      element.style.fontFamily = font;
    });
  }




  toggleFontMenu() {
    this.fontMenuOpen = !this.fontMenuOpen;
  }
  downloadAsPDF() {


    const doc = new jsPDF();
    const content = this.reportContent.nativeElement.innerHTML;
    const tempElement = document.createElement('div');
    tempElement.style.visibility = 'hidden';
    tempElement.style.position = 'absolute';
    tempElement.innerHTML = content;
    document.body.appendChild(tempElement);

    const h2Elements = tempElement.querySelectorAll('h2');
    h2Elements.forEach((h2Element: HTMLElement) => {
      const hrElement = document.createElement('hr');
      h2Element.appendChild(hrElement);
    });

    doc.setFontSize(this.fontSize);
  }


  downloadAsWord() {
    const documentCreator = new DocumentCreator();
    const doc = documentCreator.create([
      experiences,
      education,
      skills,
      achievements
    ]);

    Packer.toBlob(doc).then(blob => {
      console.log(blob);
      saveAs(blob, "example.docx");
      console.log("Document created successfully");
    });
  }

  downloadAsPDF2() {
    const documentCreator = new DocumentCreator();
    const doc = documentCreator.create([
      experiences,
      education,
      skills,
      achievements
    ]);

    const pdfDocGenerator = pdfMake.createPdf(doc);
    pdfDocGenerator.getBlob((blob) => {
      saveAs(blob, 'example.pdf');
      console.log('Document created successfully');
    });
  }
}
