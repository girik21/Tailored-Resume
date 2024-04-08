import { Project } from './project.model';

export interface Experience {
  id: string;
  position: string;
  employer: string;
  location?: string;
  startDate: Date;
  endDate?: Date;
  currentJob: boolean;
  description: string;
  companyLink?: string;
  createdDate: Date;
  projects?: Project[];
}
