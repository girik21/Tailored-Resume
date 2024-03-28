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
    this.resumeRating = this.calculateResumeRating();
    this.chatService.replyWithRating(this.resumeRating);
    this.isRatingGiven = true;
    this.isFileInputVisible = false;
  }

  attachDocument() {
    if (this.selectedFile) {
      console.log('Selected file:', this.selectedFile);
      this.selectedFile = null;
    } else {
      console.log('No file selected');
    }
  }

  calculateResumeRating(): number {
    return Math.floor(Math.random() * 101); // Generates a random number between 0 and 100
  }

  toggleFileInput() {
    this.isFileInputVisible = !this.isFileInputVisible; // Toggle file input visibility
  }
}
