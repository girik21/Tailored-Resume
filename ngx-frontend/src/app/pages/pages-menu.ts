import { NbMenuItem } from "@nebular/theme";

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: "Home",
    icon: "home-outline",
    link: "/pages/dashboard",
    home: true,
    hidden: true,
  },
  {
    title: "FEATURES",
    group: true,
  },
  {
    title: "Create",
    icon: "layout-outline",
    children: [
      {
        title: "Resume",
        link: "/pages/layout/profile",
      },

    ],
  },
  {
    title: "AI",
    icon: "keypad-outline",
    children: [
      {
        title: "AI Resume",
        link: "/pages/forms/ai-resume",
      },
    ],
  },
  {
    title: "Details",
    icon: "folder-outline",
    children: [
      {
        title: "Personal Details",
        link: "/pages/details",
        icon: "person-outline",
      },
      {
        title: "Experiences",
        link: "/pages/experiences",
        icon: "briefcase-outline",
      },
      {
        title: "Education",
        link: "/pages/education",
        icon: "book-outline",
      },
      {
        title: "Projects",
        link: "/pages/projects",
        icon: "file-outline",
      },
      {
        title: "Certifications",
        link: "/pages/certifications",
        icon: "award-outline",
      },
      {
        title: "Skills",
        link: "/pages/skills",
        icon: "star-outline",
      },
    ],
  },
];
