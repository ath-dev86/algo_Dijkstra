// Données de démonstration — à remplacer par le stockage / l'API de l'application.
export const user = {
  firstName: 'Abdou',
  hourlyRate: 14.5, // € brut / heure
  weeklyTarget: 35, // heures contractuelles
  overtimeRate: 1.25, // majoration heures sup.
};

export const week = [
  { day: 'Lun', hours: 7.5 },
  { day: 'Mar', hours: 8 },
  { day: 'Mer', hours: 6.5 },
  { day: 'Jeu', hours: 9 },
  { day: 'Ven', hours: 7 },
  { day: 'Sam', hours: 0 },
  { day: 'Dim', hours: 0 },
];

export const recentShifts = [
  { id: '1', date: 'Ven. 30 oct.', start: '08:30', end: '16:00', pause: 30, hours: 7 },
  { id: '2', date: 'Jeu. 29 oct.', start: '08:00', end: '17:30', pause: 30, hours: 9 },
  { id: '3', date: 'Mer. 28 oct.', start: '09:00', end: '16:00', pause: 30, hours: 6.5 },
  { id: '4', date: 'Mar. 27 oct.', start: '08:30', end: '17:00', pause: 30, hours: 8 },
];
