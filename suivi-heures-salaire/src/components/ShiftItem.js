import React from 'react';
import { View, Text, StyleSheet, Pressable } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { colors, radius, spacing, formatHours, formatMoney } from '../theme';

export default function ShiftItem({ shift, hourlyRate, isLast }) {
  return (
    <Pressable
      style={({ pressed }) => [styles.row, !isLast && styles.border, pressed && { opacity: 0.6 }]}
    >
      <View style={styles.iconWrap}>
        <Ionicons name="calendar-clear-outline" size={18} color={colors.primary} />
      </View>
      <View style={{ flex: 1 }}>
        <Text style={styles.date}>{shift.date}</Text>
        <Text style={styles.time}>
          {shift.start} – {shift.end} · pause {shift.pause} min
        </Text>
      </View>
      <View style={{ alignItems: 'flex-end' }}>
        <Text style={styles.hours}>{formatHours(shift.hours)}</Text>
        <Text style={styles.money}>{formatMoney(shift.hours * hourlyRate)}</Text>
      </View>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  row: { flexDirection: 'row', alignItems: 'center', paddingVertical: 14 },
  border: { borderBottomWidth: 1, borderBottomColor: colors.border },
  iconWrap: {
    width: 40,
    height: 40,
    borderRadius: radius.sm,
    backgroundColor: colors.primarySoft,
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: spacing.md,
  },
  date: { color: colors.text, fontWeight: '600', fontSize: 15 },
  time: { color: colors.textMuted, fontSize: 12, marginTop: 2 },
  hours: { color: colors.text, fontWeight: '700', fontSize: 15 },
  money: { color: colors.accent, fontWeight: '600', fontSize: 12, marginTop: 2 },
});
