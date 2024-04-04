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
      {
        title: "Cover Letter",
        link: "",
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
    ],
  },
];
