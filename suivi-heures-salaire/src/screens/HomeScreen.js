import React from 'react';
import { View, Text, StyleSheet, ScrollView, Pressable } from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import { LinearGradient } from 'expo-linear-gradient';
import { Ionicons } from '@expo/vector-icons';

import ClockCard from '../components/ClockCard';
import StatCard from '../components/StatCard';
import WeekChart from '../components/WeekChart';
import ShiftItem from '../components/ShiftItem';
import QuickAction from '../components/QuickAction';
import { colors, radius, spacing, shadow, formatMoney, formatHours } from '../theme';
import { user, week, recentShifts } from '../data/mock';

const greeting = () => {
  const h = new Date().getHours();
  if (h < 12) return 'Bonjour';
  if (h < 18) return 'Bon après-midi';
  return 'Bonsoir';
};

export default function HomeScreen() {
  const insets = useSafeAreaInsets();

  const totalHours = week.reduce((s, d) => s + d.hours, 0);
  const overtime = Math.max(totalHours - user.weeklyTarget, 0);
  const normalHours = totalHours - overtime;
  const weekSalary =
    normalHours * user.hourlyRate + overtime * user.hourlyRate * user.overtimeRate;
  // Projection mensuelle simple : moyenne hebdo × 52 / 12
  const monthProjection = (weekSalary * 52) / 12;

  const today = new Date().toLocaleDateString('fr-FR', {
    weekday: 'long',
    day: 'numeric',
    month: 'long',
  });

  return (
    <View style={styles.screen}>
      <ScrollView
        contentContainerStyle={{ paddingBottom: insets.bottom + 100 }}
        showsVerticalScrollIndicator={false}
      >
        {/* En-tête */}
        <LinearGradient
          colors={[colors.primaryDark, colors.primary]}
          style={[styles.hero, { paddingTop: insets.top + spacing.md }]}
        >
          <View style={styles.topBar}>
            <View style={styles.avatar}>
              <Text style={styles.avatarText}>{user.firstName[0]}</Text>
            </View>
            <View style={{ flex: 1, marginLeft: spacing.sm }}>
              <Text style={styles.date}>{today.charAt(0).toUpperCase() + today.slice(1)}</Text>
              <Text style={styles.hello}>
                {greeting()}, {user.firstName} 👋
              </Text>
            </View>
            <Pressable style={styles.iconBtn} accessibilityLabel="Notifications">
              <Ionicons name="notifications-outline" size={22} color={colors.white} />
              <View style={styles.notifDot} />
            </Pressable>
          </View>

          <View style={styles.salaryBlock}>
            <Text style={styles.salaryLabel}>Salaire brut estimé ce mois-ci</Text>
            <Text style={styles.salaryValue}>{formatMoney(monthProjection)}</Text>
            <View style={styles.trend}>
              <Ionicons name="trending-up" size={14} color="#34D399" />
              <Text style={styles.trendText}>+4,2 % vs mois dernier</Text>
            </View>
          </View>
        </LinearGradient>

        <View style={styles.content}>
          {/* Pointage */}
          <View style={styles.overlap}>
            <ClockCard hourlyRate={user.hourlyRate} />
          </View>

          {/* Statistiques */}
          <View style={styles.statsRow}>
            <StatCard
              icon="time-outline"
              label="Heures semaine"
              value={formatHours(totalHours)}
              hint={`Objectif ${user.weeklyTarget}h`}
              tint={colors.primary}
              tintSoft={colors.primarySoft}
            />
            <View style={{ width: spacing.md }} />
            <StatCard
              icon="wallet-outline"
              label="Gagné semaine"
              value={formatMoney(weekSalary)}
              hint={`${formatMoney(user.hourlyRate)}/h`}
              tint={colors.accent}
              tintSoft={colors.accentSoft}
            />
          </View>
          <View style={styles.statsRow}>
            <StatCard
              icon="flash-outline"
              label="Heures sup."
              value={formatHours(overtime)}
              hint={`Majorées +${Math.round((user.overtimeRate - 1) * 100)} %`}
              tint={colors.warning}
              tintSoft={colors.warningSoft}
            />
            <View style={{ width: spacing.md }} />
            <StatCard
              icon="cafe-outline"
              label="Pauses"
              value="2h00"
              hint="Cette semaine"
              tint={colors.danger}
              tintSoft={colors.dangerSoft}
            />
          </View>

          {/* Actions rapides */}
          <Text style={styles.sectionTitle}>Actions rapides</Text>
          <View style={styles.actions}>
            <QuickAction
              icon="add-circle-outline"
              label="Ajouter des heures"
              tint={colors.primary}
              tintSoft={colors.primarySoft}
            />
            <QuickAction
              icon="document-text-outline"
              label="Fiche de paie"
              tint={colors.accent}
              tintSoft={colors.accentSoft}
            />
            <QuickAction
              icon="calendar-outline"
              label="Planning"
              tint={colors.warning}
              tintSoft={colors.warningSoft}
            />
            <QuickAction
              icon="download-outline"
              label="Exporter"
              tint={colors.danger}
              tintSoft={colors.dangerSoft}
            />
          </View>

          {/* Graphique semaine */}
          <WeekChart data={week} target={user.weeklyTarget} />

          {/* Derniers services */}
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>Derniers services</Text>
            <Pressable>
              <Text style={styles.link}>Tout voir</Text>
            </Pressable>
          </View>
          <View style={styles.listCard}>
            {recentShifts.map((s, i) => (
              <ShiftItem
                key={s.id}
                shift={s}
                hourlyRate={user.hourlyRate}
                isLast={i === recentShifts.length - 1}
              />
            ))}
          </View>
        </View>
      </ScrollView>

      {/* Barre de navigation */}
      <View style={[styles.tabBar, { paddingBottom: insets.bottom || spacing.sm }]}>
        <Tab icon="home" label="Accueil" active />
        <Tab icon="time-outline" label="Heures" />
        <Tab icon="stats-chart-outline" label="Rapports" />
        <Tab icon="person-outline" label="Profil" />
      </View>
    </View>
  );
}

const Tab = ({ icon, label, active }) => (
  <Pressable style={styles.tab}>
    <Ionicons name={icon} size={22} color={active ? colors.primary : colors.textMuted} />
    <Text style={[styles.tabLabel, active && { color: colors.primary, fontWeight: '700' }]}>
      {label}
    </Text>
  </Pressable>
);

const styles = StyleSheet.create({
  screen: { flex: 1, backgroundColor: colors.bg },
  hero: {
    paddingHorizontal: spacing.lg,
    paddingBottom: 90,
    borderBottomLeftRadius: radius.xl,
    borderBottomRightRadius: radius.xl,
  },
  topBar: { flexDirection: 'row', alignItems: 'center' },
  avatar: {
    width: 44,
    height: 44,
    borderRadius: 22,
    backgroundColor: 'rgba(255,255,255,0.2)',
    borderWidth: 2,
    borderColor: 'rgba(255,255,255,0.4)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: { color: colors.white, fontWeight: '800', fontSize: 18 },
  date: { color: 'rgba(255,255,255,0.7)', fontSize: 12 },
  hello: { color: colors.white, fontSize: 18, fontWeight: '700' },
  iconBtn: {
    width: 42,
    height: 42,
    borderRadius: 14,
    backgroundColor: 'rgba(255,255,255,0.15)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  notifDot: {
    position: 'absolute',
    top: 10,
    right: 11,
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#F87171',
    borderWidth: 1.5,
    borderColor: colors.primary,
  },
  salaryBlock: { marginTop: spacing.lg },
  salaryLabel: { color: 'rgba(255,255,255,0.75)', fontSize: 13 },
  salaryValue: { color: colors.white, fontSize: 36, fontWeight: '800', marginTop: 4 },
  trend: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'flex-start',
    marginTop: spacing.sm,
    backgroundColor: 'rgba(16,185,129,0.18)',
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 999,
  },
  trendText: { color: '#A7F3D0', fontSize: 12, fontWeight: '600', marginLeft: 4 },
  content: { paddingHorizontal: spacing.md },
  overlap: { marginTop: -70 },
  statsRow: { flexDirection: 'row', marginTop: spacing.md },
  sectionHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  sectionTitle: {
    color: colors.text,
    fontSize: 17,
    fontWeight: '700',
    marginTop: spacing.lg,
    marginBottom: spacing.md,
  },
  link: { color: colors.primary, fontWeight: '600', marginTop: spacing.lg, marginBottom: spacing.md },
  actions: { flexDirection: 'row', marginBottom: spacing.lg },
  listCard: {
    backgroundColor: colors.card,
    borderRadius: radius.lg,
    paddingHorizontal: spacing.md,
    ...shadow,
  },
  tabBar: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 0,
    flexDirection: 'row',
    backgroundColor: colors.card,
    paddingTop: spacing.sm,
    borderTopLeftRadius: radius.lg,
    borderTopRightRadius: radius.lg,
    ...shadow,
    shadowOffset: { width: 0, height: -4 },
  },
  tab: { flex: 1, alignItems: 'center', paddingVertical: 4 },
  tabLabel: { fontSize: 11, color: colors.textMuted, marginTop: 2 },
});
