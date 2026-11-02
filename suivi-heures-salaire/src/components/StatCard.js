import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { colors, radius, spacing, shadow } from '../theme';

export default function StatCard({ icon, label, value, hint, tint, tintSoft }) {
  return (
    <View style={styles.card}>
      <View style={[styles.iconWrap, { backgroundColor: tintSoft }]}>
        <Ionicons name={icon} size={20} color={tint} />
      </View>
      <Text style={styles.label}>{label}</Text>
      <Text style={styles.value} numberOfLines={1} adjustsFontSizeToFit>
        {value}
      </Text>
      {hint ? <Text style={[styles.hint, { color: tint }]}>{hint}</Text> : null}
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    flex: 1,
    backgroundColor: colors.card,
    borderRadius: radius.md,
    padding: spacing.md,
    ...shadow,
  },
  iconWrap: {
    width: 38,
    height: 38,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: spacing.sm,
  },
  label: { color: colors.textMuted, fontSize: 12, fontWeight: '500' },
  value: { color: colors.text, fontSize: 20, fontWeight: '800', marginTop: 2 },
  hint: { fontSize: 12, fontWeight: '600', marginTop: 4 },
});
