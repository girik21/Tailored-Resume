export interface Certificate {
    id: string;
    name: string;
    startDate: Date;
    endDate?: Date;
    issuer: string;
    description: string;
  }
  