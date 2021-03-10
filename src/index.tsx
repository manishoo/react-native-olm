import { NativeModules } from 'react-native';

type OlmType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Olm } = NativeModules;

export default Olm as OlmType;
