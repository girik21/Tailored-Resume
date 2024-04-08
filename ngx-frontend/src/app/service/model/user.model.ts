import { Experience } from "./experience.model";
import { Skill } from "./skill.model";
import { Project } from "./project.model";
import { Education } from "./education.model";
import { Certificate } from "./certification.model";
import { Resume } from "./resume.model";
import { CoverLetter } from "./cover_letter.model";

export interface User {
    id: string;
    username: string;
    email: string;
    workEmail?: string;
    phone: string;
    address1: string;
    address2?: string;
    city: string;
    state: string;
    zip: string;
    linkedinLink: string;
    portfolioLink?: string;
    professionalSummary?: string;
    role: Role;
    createdDate?: Date;
    experiences?: Experience[];
    education?: Education[];
    projects?: Project[];
    skills?: Skill[];
    certificates?: Certificate[];
    resumes?: Resume[];
    coverLetters?: CoverLetter[];
  }
  
  export enum Role {
    USER = 'USER',
    ADMIN = 'ADMIN',
  }
  