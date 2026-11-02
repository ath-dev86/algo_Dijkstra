import React, { useEffect, useRef, useState } from 'react';
import { View, Text, StyleSheet, Pressable, Animated } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { Ionicons } from '@expo/vector-icons';
import { colors, radius, spacing, shadow, formatMoney } from '../theme';

const pad = (n) => n.toString().padStart(2, '0');
const formatTimer = (s) =>
  `${pad(Math.floor(s / 3600))}:${pad(Math.floor((s % 3600) / 60))}:${pad(s % 60)}`;

export default function ClockCard({ hourlyRate }) {
  const [running, setRunning] = useState(false);
  const [startedAt, setStartedAt] = useState(null);
  const [elapsed, setElapsed] = useState(0);
  const pulse = useRef(new Animated.Value(1)).current;

  useEffect(() => {
    if (!running) return undefined;
    const id = setInterval(() => {
      setElapsed(Math.floor((Date.now() - startedAt) / 1000));
    }, 1000);
    return () => clearInterval(id);
  }, [running, startedAt]);

  useEffect(() => {
    if (!running) {
      pulse.setValue(1);
      return undefined;
    }
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(pulse, { toValue: 0.3, duration: 700, useNativeDriver: true }),
        Animated.timing(pulse, { toValue: 1, duration: 700, useNativeDriver: true }),
      ])
    );
    loop.start();
    return () => loop.stop();
  }, [running, pulse]);

  const toggle = () => {
    if (running) {
      setRunning(false);
    } else {
      setStartedAt(Date.now() - elapsed * 1000);
      setRunning(true);
    }
  };

  const reset = () => {
    setRunning(false);
    setElapsed(0);
  };

  const earned = (elapsed / 3600) * hourlyRate;
  const startLabel = startedAt
    ? new Date(startedAt).toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })
    : '--:--';

  return (
    <LinearGradient
      colors={[colors.primary, '#7C3AED']}
      start={{ x: 0, y: 0 }}
      end={{ x: 1, y: 1 }}
      style={styles.card}
    >
      <View style={styles.header}>
        <View style={styles.status}>
          <Animated.View
            style={[
              styles.dot,
              { backgroundColor: running ? '#34D399' : 'rgba(255,255,255,0.5)', opacity: pulse },
            ]}
          />
          <Text style={styles.statusText}>{running ? 'En service' : 'Hors service'}</Text>
        </View>
        {elapsed > 0 && !running && (
          <Pressable onPress={reset} hitSlop={10} accessibilityLabel="Réinitialiser le chrono">
            <Ionicons name="refresh" size={20} color="rgba(255,255,255,0.85)" />
          </Pressable>
        )}
      </View>

      <Text style={styles.timer}>{formatTimer(elapsed)}</Text>

      <View style={styles.metaRow}>
        <View>
          <Text style={styles.metaLabel}>Début</Text>
          <Text style={styles.metaValue}>{startLabel}</Text>
        </View>
        <View style={styles.metaDivider} />
        <View>
          <Text style={styles.metaLabel}>Gagné aujourd'hui</Text>
          <Text style={styles.metaValue}>{formatMoney(earned)}</Text>
        </View>
      </View>

      <Pressable
        onPress={toggle}
        style={({ pressed }) => [styles.button, pressed && { transform: [{ scale: 0.97 }] }]}
        accessibilityRole="button"
      >
        <Ionicons
          name={running ? 'stop-circle' : 'play-circle'}
          size={24}
          color={running ? colors.danger : colors.primary}
        />
        <Text style={[styles.buttonText, { color: running ? colors.danger : colors.primary }]}>
          {running ? 'Terminer le service' : elapsed > 0 ? 'Reprendre' : 'Pointer mon arrivée'}
        </Text>
      </Pressable>
    </LinearGradient>
  );
}

const styles = StyleSheet.create({
  card: { borderRadius: radius.lg, padding: spacing.lg, ...shadow },
  header: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
  status: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'rgba(255,255,255,0.15)',
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 999,
  },
  dot: { width: 8, height: 8, borderRadius: 4, marginRight: 8 },
  statusText: { color: colors.white, fontWeight: '600', fontSize: 13 },
  timer: {
    color: colors.white,
    fontSize: 48,
    fontWeight: '800',
    letterSpacing: 1,
    marginTop: spacing.md,
    fontVariant: ['tabular-nums'],
  },
  metaRow: { flexDirection: 'row', alignItems: 'center', marginTop: spacing.sm },
  metaLabel: { color: 'rgba(255,255,255,0.7)', fontSize: 12 },
  metaValue: { color: colors.white, fontSize: 16, fontWeight: '700', marginTop: 2 },
  metaDivider: {
    width: 1,
    height: 30,
    backgroundColor: 'rgba(255,255,255,0.25)',
    marginHorizontal: spacing.md,
  },
  button: {
    marginTop: spacing.lg,
    backgroundColor: colors.white,
    borderRadius: radius.md,
    paddingVertical: 14,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: { fontSize: 16, fontWeight: '700', marginLeft: 8 },
});
