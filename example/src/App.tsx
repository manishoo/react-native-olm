import * as React from 'react';
import { StyleSheet, View } from 'react-native';
import Olm from 'react-native-olm';

export default function App() {
  React.useEffect(() => {
    Olm.init();
  }, []);

  return <View style={styles.container} />;
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
