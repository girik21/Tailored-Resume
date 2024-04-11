import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NbComponentShape, NbComponentSize, NbComponentStatus } from '@nebular/theme';
import { AngularFireAuth } from '@angular/fire/compat/auth';

import { UserAPI } from '../../../service/api/user-api.service';
import { User } from '../../../service/model/user.model';

@Component({
  selector: 'ngx-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.scss'],
})
export class ProfileComponent implements OnInit {

  personalDetails: FormGroup;
  
  experienceForm: FormGroup;
  experienceGroups: FormGroup[] = [];

  projectForm: FormGroup;
  projectGroups: FormGroup[] = [];

  educationForm: FormGroup;
  educationGroups: FormGroup[] = [];

  certificationForm: FormGroup;
  certificationGroups: FormGroup[] = [];

  skillForm: FormGroup;
  skillGroups: FormGroup[] = [];

  professionalSummary: FormGroup;
  userId: string

  status: NbComponentStatus =  'primary' ;
  shapes: NbComponentShape[] = [ 'rectangle', 'semi-round', 'round' ];
  size: NbComponentSize =  'tiny';

  formData: any = {}; // formData property for aggregated form data

  loading: boolean = false;

  jobPosition: string = null;
  company: string = null;

  timer: any;
  seconds: number = 0;
  constructor(
    private fb: FormBuilder, 
    private userAPI: UserAPI, 
    private router: Router, 
    private fireAuth: AngularFireAuth
  ) {
  }

  startTimer() {
    this.timer = setInterval(() => {
      this.seconds++;
    }, 1000);
  }

  stopTimer() {
    clearInterval(this.timer);
  }

  ngOnInit() {

    // Initialize the forms
    this.initPersonalDetailsForm();
    this.initProfessionalSummaryForm();

    // For experiences
    this.experienceForm = this.fb.group({
      experiences: this.fb.array([]) // Initialize an empty array for experiences
    });

    // For projects
    this.projectForm = this.fb.group({
      projects: this.fb.array([]) // Initialize an empty array for projects
    });

    // For educations
    this.educationForm = this.fb.group({
      educations: this.fb.array([]) // Initialize an empty array for educations
    });

    // For certifications
    this.certificationForm = this.fb.group({
      certifications: this.fb.array([]) // Initialize an empty array for certifications
    });

    // For skills
    this.skillForm = this.fb.group({
      skills: this.fb.array([]) // Initialize an empty array for skills
    });

    // Get Logged In User Data
    this.getUserData();
  }

  initPersonalDetailsForm() {
    this.personalDetails = this.fb.group({
      username: ['', Validators.required],
      professionalEmail: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      address1: ['', Validators.required],
      address2: [''],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zip: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      linkedinLink: ['', Validators.required],
      portfolioLink: [''],
    });
  }

  initProfessionalSummaryForm() {
    this.professionalSummary = this.fb.group({
      professionalSummary: ['', Validators.required],
    });
  }

  getUserData() {
    const loggedInEmail = localStorage.getItem("loggedInEmail")
    const accessToken = localStorage.getItem("accessToken")

    // Make a request to fetch user data based on the userId
    this.userAPI.getAllUsersByEmail(loggedInEmail, accessToken).subscribe(
      (userData: User[]) => {

        if (userData && userData.length > 0) {

          // Prefill the personal details form with user's data
          this.prefillPersonalDetailsForm(userData[0]);
          this.prefillProfessionalSummaryForm(userData[0]);

          this.prefillExperiencesForm(userData[0].experiences);

          // Prefill projects if present in user data
          if (userData[0].projects.length > 0) {
            this.prefillProjectForm(userData[0].projects);
          } else {
            // If no projects, add the first experience form
            this.addProject();
          }

          this.prefillEducationForm(userData[0].education);

          // Prefill certificates if present in user data
          if (userData[0].certificates.length > 0) {
            this.prefillCertificationForm(userData[0].certificates);
          } else {
            // If no education, add the first experience form
            this.addCertification();
          }

          this.prefillSkillsForm(userData[0].skills);
          
        } else {
          this.addExperience()
          this.addEducation()
          this.addProject()
          this.addCertification()
          this.addSkill()
        }
      },
      (error) => {
        console.error('Error fetching user data:', error);
        // Handle error
      }
    );
  }

  prefillPersonalDetailsForm(userData: User) {
    this.personalDetails.patchValue({
      username: userData.username || '',
      professionalEmail: userData.professionalEmail || '',      
      phone: userData.phone || '', 
      address1: userData.address1 || '', 
      address2: userData.address2 || '', 
      city: userData.city || '', 
      state: userData.state || '', 
      zip: userData.zip || '', 
      linkedinLink: userData.linkedinLink || '', 
      portfolioLink: userData.portfolioLink || '', 
    });
  }

  prefillProfessionalSummaryForm(userData: User) {
    this.professionalSummary.patchValue({
      professionalSummary: userData.professionalSummary || '', 
    });
  }

  prefillExperiencesForm(experiences: any[]) {
    // Prefill experiences form if present in user data
    experiences.forEach((experience) => {
      const experienceGroup = this.createExperienceGroup();
      experienceGroup.patchValue(experience);
      this.experienceGroups.push(experienceGroup);
      (this.experienceForm.get('experiences') as FormArray).push(experienceGroup);
    });
  }

  prefillProjectForm(projects: any[]) {
    // Prefill projects form if present in user data
    projects.forEach((project) => {
      const projectGroup = this.createProjectGroup();
      projectGroup.patchValue(project);
      this.projectGroups.push(projectGroup);
      (this.projectForm.get('projects') as FormArray).push(projectGroup);
    });
  }

  prefillEducationForm(education: any[]) {
    // Prefill education form if present in user data
    education.forEach((singleEducation) => {
      const educationGroup = this.createEducationGroup();
      educationGroup.patchValue(singleEducation);
      this.educationGroups.push(educationGroup);
      (this.educationForm.get('educations') as FormArray).push(educationGroup);
    });
  }

  prefillCertificationForm(certicates: any[]) {
    // Prefill certicates form if present in user data
    certicates.forEach((certification) => {
      const certificationGroup = this.createCertificationGroup();
      certificationGroup.patchValue(certification);
      this.certificationGroups.push(certificationGroup);
      (this.certificationForm.get('certifications') as FormArray).push(certificationGroup);
    });
  }

  prefillSkillsForm(skills: any[]) {
    // Prefill skills form if present in user data
    skills.forEach((skill) => {
      const skillGroup = this.createSkillGroup();
      skillGroup.patchValue(skill);
      this.skillGroups.push(skillGroup);
      (this.skillForm.get('skills') as FormArray).push(skillGroup);
    });
  }

  // for creating a new experience form
  createExperienceGroup(): FormGroup {
    return this.fb.group({
      position: ['', Validators.required],
      employer: ['', Validators.required],
      location: ['', Validators.required],
      startDate: [''],
      endDate: [''],
      currentJob: [''],
      companyLink: [''],
      description: ['', Validators.required]
    });
  }

  // add experience in the form
  addExperience() {
    const experienceGroup = this.createExperienceGroup();
    this.experienceGroups.push(experienceGroup);
    (this.experienceForm.get('experiences') as FormArray).push(experienceGroup);
  }

  // remove the experience form
  removeExperience(index: number) {
    (this.experienceForm.get('experiences') as FormArray).removeAt(index);
    this.experienceGroups.splice(index, 1);
  }

  toggleEndDate(index: number): void {
    const experiencesArray = this.experienceForm.get('experiences') as FormArray;
    const experienceGroup = experiencesArray.at(index);
    const currentJobControl = experienceGroup.get('currentJob');
    const endDateControl = experienceGroup.get('endDate');
  
    if (endDateControl && currentJobControl) {
      if (currentJobControl.value) {
        endDateControl.setValue('');
        endDateControl.disable();
      } else {
        endDateControl.enable();
      }
    }
  }
  
  isEndDateDisabled(index: number): boolean {
    const experiencesArray = this.experienceForm.get('experiences') as FormArray;
    const experienceGroup = experiencesArray.at(index);
    const currentJobControl = experienceGroup.get('currentJob');
  
    return currentJobControl ? currentJobControl.value : false;
  }
  
  // for creating a new project form
  createProjectGroup(): FormGroup {
    return this.fb.group({
      name: [''],
      link: [''],
      employer: [''],
      description: [''],
      startDate: [''],
      endDate: ['']
    });
  }

  // add project in the form
  addProject() {
    const projectGroup = this.createProjectGroup();
    this.projectGroups.push(projectGroup);
    (this.projectForm.get('projects') as FormArray).push(projectGroup);
  }

  // remove the project form
  removeProject(index: number) {
    (this.projectForm.get('projects') as FormArray).removeAt(index);
    this.projectGroups.splice(index, 1);
  }

  // for creating a new education form
  createEducationGroup(): FormGroup {
    return this.fb.group({
      degreeName: ['', Validators.required],
      school: ['', Validators.required],
      location: ['', Validators.required],
      major: ['', Validators.required],
      minor: [''],
      startDate: [''],
      endDate: [''],
      graduated: [''],
      grade: [''],
      description: ['']
    });
  }

  // add education in the form
  addEducation() {
    const educationGroup = this.createEducationGroup();
    this.educationGroups.push(educationGroup);
    (this.educationForm.get('educations') as FormArray).push(educationGroup);
  }

  // remove the education form
  removeEducation(index: number) {
    (this.educationForm.get('educations') as FormArray).removeAt(index);
    this.educationGroups.splice(index, 1);
  }

  // for creating a new certification form
  createCertificationGroup(): FormGroup {
    return this.fb.group({
      name: [''],
      issuer: [''],
      startDate: [''],
      endDate: [''],
      description: ['']
    });
  }

  // add certification in the form
  addCertification() {
    const certificationGroup = this.createCertificationGroup();
    this.certificationGroups.push(certificationGroup);
    (this.certificationForm.get('certifications') as FormArray).push(certificationGroup);
  }

  // remove the certification form
  removeCertification(index: number) {
    (this.certificationForm.get('certifications') as FormArray).removeAt(index);
    this.certificationGroups.splice(index, 1);
  }

  // Add a method to toggle the end date based on graduation status
  toggleEducationEndDate(index: number): void {
    const educationsArray = this.educationForm.get('educations') as FormArray;
    const educationGroup = educationsArray.at(index);
    const graduatedControl = educationGroup.get('graduated');
    const endDateControl = educationGroup.get('endDate');

    if (graduatedControl && endDateControl) {
      if (graduatedControl.value) {
        endDateControl.enable(); // If graduated, enable end date
      } else {
        endDateControl.disable(); // If not graduated, disable end date
      }
    }
  }

  // Add a method to check if end date should be disabled based on graduation status
  isEducationEndDateDisabled(index: number): boolean {
    const educationsArray = this.educationForm.get('educations') as FormArray;
    const educationGroup = educationsArray.at(index);
    const graduatedControl = educationGroup.get('graduated');

    return graduatedControl ? !graduatedControl.value : true; // Return true if not graduated
  }

  // for creating a new skill form
  createSkillGroup(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required]
    });
  }

  // add skill in the form
  addSkill() {
    const skillGroup = this.createSkillGroup();
    this.skillGroups.push(skillGroup);
    (this.skillForm.get('skills') as FormArray).push(skillGroup);
  }

  // remove the skill form
  removeSkill(index: number) {
    (this.skillForm.get('skills') as FormArray).removeAt(index);
    this.skillGroups.splice(index, 1);
  }

  async submitForms() {
    try {
      
      const loggedInEmail = localStorage.getItem("loggedInEmail")
      const accessToken = localStorage.getItem("accessToken")

      // Combine professional summary with personal details
      const personalDetailsWithSummary = {
        ...this.personalDetails.value,
        email: loggedInEmail,
        professionalSummary: this.professionalSummary.value.professionalSummary,
        role: 'USER'
      };

      // Make a request to fetch user data based on the userId
      this.userAPI.getAllUsersByEmail(loggedInEmail, accessToken).subscribe(
        async (userData: User[]) => {
          if (userData && userData.length > 0) {
            // Update personal details including professional summary
            await this.userAPI.updateUserDetails(personalDetailsWithSummary, userData[0].id).toPromise();
            this.userId = userData[0].id

            // Delete existing experiences
            for (const experience of userData[0].experiences) {
              await this.userAPI.deleteExperience(experience.id).toPromise();
            }

            // Delete existing education
            for (const education of userData[0].education) {
              await this.userAPI.deleteEducation(education.id).toPromise();
            }

            // Delete existing projects
            if (userData[0].projects.length > 0) {
              for (const project of userData[0].projects) {
                await this.userAPI.deleteProject(project.id).toPromise();
              }
            }

            // Delete existing skills
            for (const skill of userData[0].skills) {
              await this.userAPI.deleteSkill(skill.id).toPromise();
            }

            // Delete existing certifications
            if (userData[0].certificates.length > 0) {
              for (const certification of userData[0].certificates) {
                await this.userAPI.deleteCertification(certification.id).toPromise();
              }
            }

            //Let's save the new ones
            // Save each experience
            for (const experienceGroup of this.experienceGroups) {
              await this.userAPI.saveExperience(experienceGroup.value, this.userId).toPromise();
            }

            // Check if there are projects and save if not empty
            const projectsArray = this.projectForm.get('projects') as FormArray;
            if (projectsArray.length > 0) {
              const checkProjectGroup = this.projectGroups[0];
              if (checkProjectGroup.value.name !== '' || checkProjectGroup.value.startDate !== '' || checkProjectGroup.value.endDate !== '' || checkProjectGroup.value.employer !== '' || checkProjectGroup.value.description !== '') {
                for (const projectGroup of this.projectGroups) {
                  await this.userAPI.saveProjects(projectGroup.value, this.userId).toPromise();
                }
              }
            }

            // Save each education
            for (const educationGroup of this.educationGroups) {
              await this.userAPI.saveEducation(educationGroup.value, this.userId).toPromise();
            }

            // Check if there are certifications and save if not empty
            const certificationsArray = this.certificationForm.get('certifications') as FormArray;
            if (certificationsArray.length > 0) {
              const checkCertificationGroup = this.certificationGroups[0];
              if (checkCertificationGroup.value.name !== '' || checkCertificationGroup.value.startDate !== '' || checkCertificationGroup.value.endDate !== '' || checkCertificationGroup.value.issuer !== '' || checkCertificationGroup.value.description !== '') {
                for (const certificationGroup of this.certificationGroups) {
                  await this.userAPI.saveCertifications(certificationGroup.value, this.userId).toPromise();
                }
              }
            }

            // Save each skill
            for (const skillGroup of this.skillGroups) {
              await this.userAPI.saveSkills(skillGroup.value, this.userId).toPromise();
            }
          } else {

            // Save personal details including professional summary
            const userDetailsResponse = await this.userAPI.saveUserDetails(personalDetailsWithSummary).toPromise();
            this.userId = userDetailsResponse.data.id;


            // Save each experience
            for (const experienceGroup of this.experienceGroups) {
              await this.userAPI.saveExperience(experienceGroup.value, this.userId).toPromise();
            }

            // Check if there are projects and save if not empty
            const projectsArray = this.projectForm.get('projects') as FormArray;
            if (projectsArray.length > 0) {
              const checkProjectGroup = this.projectGroups[0];
              if (checkProjectGroup.value.name !== '' || checkProjectGroup.value.startDate !== '' || checkProjectGroup.value.endDate !== '' || checkProjectGroup.value.employer !== '' || checkProjectGroup.value.description !== '') {
                for (const projectGroup of this.projectGroups) {
                  await this.userAPI.saveProjects(projectGroup.value, this.userId).toPromise();
                }
              }
            }

            // Save each education
            for (const educationGroup of this.educationGroups) {
              await this.userAPI.saveEducation(educationGroup.value, this.userId).toPromise();
            }

            // Check if there are certifications and save if not empty
            const certificationsArray = this.certificationForm.get('certifications') as FormArray;
            if (certificationsArray.length > 0) {
              const checkCertificationGroup = this.certificationGroups[0];
              if (checkCertificationGroup.value.name !== '' || checkCertificationGroup.value.startDate !== '' || checkCertificationGroup.value.endDate !== '' || checkCertificationGroup.value.issuer !== '' || checkCertificationGroup.value.description !== '') {
                for (const certificationGroup of this.certificationGroups) {
                  await this.userAPI.saveCertifications(certificationGroup.value, this.userId).toPromise();
                }
              }
            }

            // Save each skill
            for (const skillGroup of this.skillGroups) {
              await this.userAPI.saveSkills(skillGroup.value, this.userId).toPromise();
            }
          }
        }
      );
    } catch (error) {
      console.error('Error occurred while saving user details:', error);
    }
  }

  getFormData(): any {
    let experienceData = [];
    for (const experienceGroup of this.experienceGroups) {
      experienceData.push(experienceGroup.value);
    }
    this.formData = {
      "Personal Details": this.personalDetails.value,
      //'Experience': experienceData
      //,
      // 'Project':  this.projectForm.value,
      // 'Education': this.educationForm.value,
      // 'Certifications': this.certificationForm.value,
      // 'Skills': this.skillForm.value,
      "Professional Summary": this.professionalSummary.value,
    };

    return this.formData;
  }

  onGenerateResponsibilitiesClick(index: number) {
    if (this.jobPosition === null || this.company === null){
      alert("Please fill out the following details \n - Job Position \n - Company name" ); 
      return; 
    }
    this.loading = true;
    const requestBody = { jobPosition:this.jobPosition, company:this.company };

    // alert(`${this.jobPosition}, \n ${this.company}`);

    this.startTimer();

    this.userAPI.generateResponsibilities2(requestBody, this.userId).subscribe(
      (response) => {
        this.loading = false;
        this.stopTimer();
        //console.log(response)
        this.updateTextArea(index, response.data);
        
      },
      (error: any) => {
        console.error("Error fetching responsibilities:", error);
        this.loading = false;
        this.stopTimer();
      }
    );
  }

  updateTextArea(index: number, responsibilities) {
    const formattedResponsibilities = responsibilities
      .map((responsibility) => `- ${responsibility.responsibility}`)
      .join("\n");
    const controlName = this.getFormControlName(index);
    //console.log(formattedResponsibilities);
    this.experienceForm.get(controlName).patchValue(formattedResponsibilities);
  }

  getFormControlName(index: number): string {
    return `experiences.${index}.description`;
  }
}

