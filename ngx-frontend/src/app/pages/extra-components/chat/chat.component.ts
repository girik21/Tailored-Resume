import { Component } from '@angular/core';
import { ChatService } from './chat.service';

@Component({
  selector: 'ngx-chat',
  templateUrl: 'chat.component.html',
  styleUrls: ['chat.component.scss'],
  providers: [ChatService],
})
export class ChatComponent {
  isChatboxOpen: boolean = false;
  isFileInputVisible: boolean = false;
  isRatingGiven: boolean = false;
  messages: any[];
  selectedFile: File | null = null;
  resumeRating: number | null = null;

  constructor(protected chatService: ChatService) {
    this.messages = this.chatService.loadMessages();
  }

  toggleChatbox() {
    this.isChatboxOpen = !this.isChatboxOpen;
  }

  sendMessage(event: any) {
    const files = !event.files ? [] : event.files.map((file) => {
      return {
        url: file.src,
        type: file.type,
        icon: 'nb-compose',
      };
    });

    this.messages.push({
      text: event.message,
      date: new Date(),
      reply: true,
      type: files.length ? 'file' : 'text',
      files: files,
      user: {
        name: 'Jonh Doe',
        avatar: 'https://i.gifer.com/no.gif',
      },
    });
    const botReply = this.chatService.reply(event.message);
    if (botReply) {
      setTimeout(() => { this.messages.push(botReply); }, 500);
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        const text = reader.result as string;
        console.log('File content:', text);
        this.resumeRating = this.calculateResumeRating(text);
        this.chatService.replyWithRating(this.resumeRating);
        this.isRatingGiven = true;
        this.isFileInputVisible = false;
      };
      reader.readAsText(this.selectedFile);
    } else {
      console.log('No file selected');
    }
  }


  attachDocument() {
    if (this.selectedFile) {
      console.log('Selected file:', this.selectedFile);
      this.selectedFile = null;
    } else {
      console.log('No file selected');
    }
  }

  calculateResumeRating(text: string): number {
    // Expanded list of keywords and action verbs
    const keywords = [
      'Engineered', 'Reduced', 'achievements', 'job education', 'projects', 'mentorships',
      'led', 'responsible for', 'managed', 'applied', 'adopted', 'deployed', 'employed',
      'exerted', 'handled', 'mobilized', 'operated', 'promoted', 'profit by', 'put to use',
      'restored', 'revived', 'resorted to', 'specialize in', 'organized', 'experimented',
      'detected', 'discovered', 'measured', 'mapped', 'probed', 'searched', 'surveyed',
      'studied', 'analyzed', 'assessed', 'clarified', 'checked', 'examined', 'explored',
      'evaluated', 'investigated', 'quantified', 'reviewed', 'tested', 'tracked', 'convert',
      'critiqued', 'diagnosed', 'identified', 'invented', 'proved', 'solved', 'collaborated',
      'fostered', 'streamlined', 'coordinated', 'executed', 'negotiated', 'sold', 'closed',
      'assisted', 'resolved', 'helped', 'adjusted', 'analyzed', 'appraised', 'assessed',
      'audited', 'balanced', 'budgeted', 'calculated', 'converted', 'estimated', 'evaluated',
      'forecasted', 'invested', 'lowered', 'measured', 'netted', 'projected', 'qualified',
      'reduced', 'researched'
    ];

    // Regular expression to match percentage values
    const percentageRegex = /(\d+(\.\d+)?%)/g;

    let matches = 0;
    let percentageMatches = 0;

    keywords.forEach(keyword => {
      const regex = new RegExp(keyword, 'gi');
      const found = text.match(regex);
      if (found) {
        matches += found.length; // Increase matches count based on the number of matches
      }
    });

    // Count percentage matches
    const percentageMatchesArray = text.match(percentageRegex);
    if (percentageMatchesArray) {
      percentageMatches = percentageMatchesArray.length;
    }

    // Combine keyword matches and percentage matches for the rating
    const totalMatches = matches + percentageMatches;

    // Calculate rating based on the number of matches
    let rating = 0;
    if (totalMatches === keywords.length + 1) { // Assuming one percentage match is required
      rating = 90;
    } else if (totalMatches === (keywords.length + 1) / 2) {
      rating = 60; // Half of the keywords and one percentage match found, rating is 50
    } else if (totalMatches > 0) {
      rating = 45; // Some keywords and/or percentage matches found, rating is 25
    }

    return rating;
  }


  toggleFileInput() {
    this.isFileInputVisible = !this.isFileInputVisible;
  }
}
