export interface Project {
  id: string;
  name: string;
  startDate: Date;
  endDate?: Date;
  employer: string;
  link?: string;
  description: string;
}
