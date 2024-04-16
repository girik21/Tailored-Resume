import { Component } from '@angular/core';
import { NbComponentShape, NbComponentSize, NbComponentStatus } from '@nebular/theme';
import { UserAPI } from '../../../service/api/user-api.service';

@Component({
  selector: 'ngx-buttons',
  styleUrls: ['./ai-resume.component.scss'],
  templateUrl: './ai-resume.component.html',
})
export class AiResumeComponent {
  status: NbComponentStatus = 'primary' ;
  shapes: NbComponentShape[] = [ 'rectangle', 'semi-round', 'round' ];
  size: NbComponentSize =  'tiny';

  jobDescription: string = null;
  loading: boolean = false;

  timer: any;
  seconds: number = 0;

  userId: string;
  formData : any
  constructor(private userAPI: UserAPI, ) {}

  startTimer() {
    this.timer = setInterval(() => {
      this.seconds++;
    }, 1000);
  }

  stopTimer() {
    clearInterval(this.timer);
  }

  ngOnDestroy() {
    this.stopTimer(); // Make sure to stop the timer when the component is destroyed
  }

  onGenerateResume() {

    // if (this.jobDescription === null ){
    //   alert("Please fill out the following details \n - Job Position" ); 
    //   return; 
    // }
    this.loading = true;
    const requestBody = { jobDesc:this.jobDescription };

    // alert(`${this.jobDescription}, \n ${this.company}`);

    this.startTimer();
    this.loading = false;
    this.formData = {
      "Personal Details": {
        "username": "John Doe",
        "phone": "123-456-7890",
        "linkedinLink": "linkedin.com/in/johndoe",
        "email": "john.doe@example.com",
        "address1": "123 Main St",
        "city": "Anytown",
        "state": "Some State",
        "zip": "12345"
      },
      "Professional Summary": {
        "professionalSummary": "Experienced software engineer with expertise in web development and a passion for solving complex problems."
      },
      "Experience": [
        {
          "employer": "ABC Inc.",
          "startDate": "January 2018",
          "endDate": "Present",
          "position": "Senior Software Engineer",
          "description": "Lead a team of developers to build scalable web applications using Angular and Node.js."
        },
        {
          "employer": "XYZ Corp.",
          "startDate": "June 2015",
          "endDate": "December 2017",
          "position": "Software Engineer",
          "description": "Developed RESTful APIs and implemented frontend interfaces for enterprise applications."
        }
      ],
      "Project": [
        {
          "name": "Project A",
          "startDate": "March 2019",
          "endDate": "May 2020",
          "employer": "ABC Inc.",
          "description": "Implemented a microservices architecture for a high-traffic e-commerce platform."
        },
        {
          "name": "Project B",
          "startDate": "September 2016",
          "endDate": "May 2017",
          "employer": "XYZ Corp.",
          "description": "Designed and developed a real-time chat application using WebSocket technology."
        }
      ],
      "Education": [
        {
          "degreeName": "Bachelor of Science in Computer Science",
          "startDate": "September 2011",
          "endDate": "May 2015",
          "school": "University of Example",
          "description": "Focused on software engineering principles and algorithms."
        }
      ],
      "Certifications": [
        {
          "name": "AWS Certified Solutions Architect",
          "startDate": "July 2018",
          "endDate": "July 2021",
          "issuer": "Amazon Web Services",
          "description": "Demonstrated expertise in designing distributed systems on AWS."
        }
      ],
      "Skills": ["JavaScript", "TypeScript", "Angular", "Node.js", "HTML", "CSS", "RESTful APIs"]
    };this.stopTimer();
    // this.userAPI.generateResume(requestBody, this.userId).subscribe(
    //   (response) => {
    //     this.loading = false;
    //     this.stopTimer();
       
    //     this.updateReportViewer(response.data);
        
    //   },
    //   (error: any) => {
    //     console.error("Error fetching responsibilities:", error);
    //     this.loading = false;
    //     this.stopTimer();
    //   }
    // );
  }

  onGenerateResume2() {
    if (this.jobDescription === null ){
      alert("Please fill out the Job Description"); 
      return; 
    }
    
    
    this.loading = true;
    const requestBody = { jobDesc:this.jobDescription};   

    this.startTimer();
    let loggedInEmail = localStorage.getItem('loggedInEmail');
    this.userAPI.generateResume(requestBody, loggedInEmail).subscribe(
      (response) => {
        this.loading = false;
        this.stopTimer();        
        
        this.formData = response.data
      },
      (error: any) => {
        console.error("Error fetching responsibilities:", error);
        this.loading = false;
        this.stopTimer();
      }
    );
  }

  updateReportViewer(response: any){

  }

  getFormData() {
    return this.formData
  }
}
