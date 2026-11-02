import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { colors, radius, spacing, shadow, formatHours } from '../theme';

const CHART_HEIGHT = 120;

export default function WeekChart({ data, target }) {
  const dailyTarget = target / 5;
  const max = Math.max(dailyTarget, ...data.map((d) => d.hours)) * 1.1;
  const total = data.reduce((s, d) => s + d.hours, 0);
  const progress = Math.min(total / target, 1);
  const todayIndex = (new Date().getDay() + 6) % 7;

  return (
    <View style={styles.card}>
      <View style={styles.header}>
        <View>
          <Text style={styles.title}>Cette semaine</Text>
          <Text style={styles.subtitle}>
            {formatHours(total)} sur {target}h prévues
          </Text>
        </View>
        <View style={styles.badge}>
          <Text style={styles.badgeText}>{Math.round(progress * 100)}%</Text>
        </View>
      </View>

      <View style={styles.progressTrack}>
        <View style={[styles.progressFill, { width: `${progress * 100}%` }]} />
      </View>

      <View style={styles.chart}>
        <View
          style={[styles.targetLine, { bottom: (dailyTarget / max) * CHART_HEIGHT + 22 }]}
        />
        {data.map((d, i) => {
          const isToday = i === todayIndex;
          const over = d.hours > dailyTarget;
          return (
            <View key={d.day} style={styles.barCol}>
              <View style={styles.barTrack}>
                <View
                  style={[
                    styles.bar,
                    {
                      height: Math.max((d.hours / max) * CHART_HEIGHT, d.hours ? 6 : 4),
                      backgroundColor: d.hours
                        ? over
                          ? colors.warning
                          : colors.primary
                        : colors.border,
                    },
                  ]}
                />
              </View>
              <Text style={[styles.day, isToday && styles.dayToday]}>{d.day}</Text>
            </View>
          );
        })}
      </View>

      <View style={styles.legend}>
        <Legend color={colors.primary} label="Heures normales" />
        <Legend color={colors.warning} label="Heures sup." />
      </View>
    </View>
  );
}

const Legend = ({ color, label }) => (
  <View style={styles.legendItem}>
    <View style={[styles.legendDot, { backgroundColor: color }]} />
    <Text style={styles.legendText}>{label}</Text>
  </View>
);

const styles = StyleSheet.create({
  card: { backgroundColor: colors.card, borderRadius: radius.lg, padding: spacing.lg, ...shadow },
  header: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'flex-start' },
  title: { color: colors.text, fontSize: 17, fontWeight: '700' },
  subtitle: { color: colors.textMuted, fontSize: 13, marginTop: 2 },
  badge: {
    backgroundColor: colors.primarySoft,
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 999,
  },
  badgeText: { color: colors.primary, fontWeight: '700', fontSize: 13 },
  progressTrack: {
    height: 8,
    backgroundColor: colors.primarySoft,
    borderRadius: 4,
    marginTop: spacing.md,
    overflow: 'hidden',
  },
  progressFill: { height: '100%', backgroundColor: colors.primary, borderRadius: 4 },
  chart: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-end',
    marginTop: spacing.lg,
    height: CHART_HEIGHT + 22,
  },
  targetLine: {
    position: 'absolute',
    left: 0,
    right: 0,
    borderTopWidth: 1,
    borderStyle: 'dashed',
    borderColor: colors.textMuted,
    opacity: 0.4,
  },
  barCol: { flex: 1, alignItems: 'center' },
  barTrack: { height: CHART_HEIGHT, justifyContent: 'flex-end' },
  bar: { width: 22, borderRadius: 8 },
  day: { color: colors.textMuted, fontSize: 12, marginTop: 6, height: 16 },
  dayToday: { color: colors.primary, fontWeight: '800' },
  legend: { flexDirection: 'row', marginTop: spacing.md },
  legendItem: { flexDirection: 'row', alignItems: 'center', marginRight: spacing.md },
  legendDot: { width: 8, height: 8, borderRadius: 4, marginRight: 6 },
  legendText: { color: colors.textMuted, fontSize: 12 },
});
