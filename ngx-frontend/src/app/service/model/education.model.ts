export interface Education {
  id: string;
  degreeName: string;
  major: string;
  minor?: string;
  school: string;
  location: string;
  startDate: Date;
  endDate?: Date;
  graduated: boolean;
  grade?: number;
  description?: string;
  createdDate: Date;
}
