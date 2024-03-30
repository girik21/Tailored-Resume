import { Injectable } from '@angular/core';
import { botReplies } from './bot-replies';
import { messages } from './messages';

const botAvatar: string = 'https://i.ytimg.com/vi/Erqi5ckVoEo/hqdefault.jpg';


@Injectable()
export class ChatService {

  loadMessages() {
    return messages;
  }

  loadBotReplies() {
    return botReplies;
  }

  reply(message: string) {
    const botReply: any = this.loadBotReplies()
      .find((reply: any) => message.search(reply.regExp) !== -1);

    if (botReply.reply.type === 'quote') {
      botReply.reply.quote = message;
    }

    botReply.reply.text = botReply.answerArray[Math.floor(Math.random() * botReply.answerArray.length)];
    return { ...botReply.reply };
  }

  replyWithRating(rating: number) {
    // Create a bot reply for the resume rating
    const ratingBotReply = {
      text: `Thank you for submitting! Your resume has been rated above ${rating} %.  Create a tailored resume to beat the ats score by a good margin `,
      reply: false,
      date: new Date(),
      user: {
        name: 'Tailored Bot',
        avatar: botAvatar,
      },
    };
    messages.push(ratingBotReply);
  }
}
