const botAvatar: string = 'https://i.ytimg.com/vi/Erqi5ckVoEo/hqdefault.jpg';

export const botReplies = [
  {
    regExp: /([H,h]ey)|([H,h]i)/g,
    answerArray: ['Hello!', 'Hi', 'Yes?', 'Yes, milord?', 'What can I do for you?'],
    type: 'text',
    reply: {
      text: '',
      reply: false,
      date: new Date(),
      user: {
        name: 'Bot',
        avatar: botAvatar,
      },
    },
  },
  {
    regExp: /([R,r]esume)|(RESUME)/g,
    type: 'resume',
    answerArray: ['Let me assist you with that! Go to the Create section and add your details we can get started with that'],
    reply: {
      text: '',
      reply: false,
      date: new Date(),
      user: {
        name: 'Bot',
        avatar: botAvatar,
      },
    },
  },
  {
    regExp: /([C,c]over [L,l]etter)|(COVER LETTER)/g,
    type: 'cover-letter',
    answerArray: ['Let me assist you with that! Go to the AI navbar and add your details we can get started with that'],
    reply: {
      text: '',
      reply: false,
      date: new Date(),
      user: {
        name: 'Bot',
        avatar: botAvatar,
      },
    },
  },
];
