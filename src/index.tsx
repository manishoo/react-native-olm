import { NativeModules } from 'react-native';

let { ReactNativeOlm } = NativeModules;

ReactNativeOlm.init();

const log = (..._args: any[]) => {
  console.log(..._args);
};

const Olm = {
  init: ReactNativeOlm.init,
  get_library_version: ReactNativeOlm.getOlmLibVersion,
  PRIVATE_KEY_LENGTH: ReactNativeOlm.PRIVATE_KEY_LENGTH(),
  SAS: class SAS {
    constructor() {
      // ReactNativeOlm.init();
      ReactNativeOlm.createSAS();
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freeSAS();
      log('free FINISHED');

      return result;
    }

    get_pubkey() {
      log('logget_pubkey');
      const result = ReactNativeOlm.getSASPubKey();
      log('get_pubkey FINISHED');

      return result;
    }

    set_their_key(their_key: string) {
      log('set_their_key');
      const result = ReactNativeOlm.setTheirSASKey(their_key);
      log('set_their_key FINISHED');

      return result;
    }

    generate_bytes(info: string, length: number) {
      log('generate_bytes');
      const result = new Uint8Array(
        ReactNativeOlm.generateSASBytes(info, length)
      );
      log('generate_bytes FINISHED');

      return result;
    }

    calculate_mac(input: string, info: string) {
      log('calculate_mac');
      const result = ReactNativeOlm.calculateSASMac(input, info);
      log('calculate_mac FINISHED');

      return result;
    }

    calculate_mac_long_kdf(input: string, info: string) {
      log('calculate_mac_long_kdf');
      const result = ReactNativeOlm.calculateSASMacLongKdf(input, info);
      log('calculate_mac_long_kdf FINISHED');

      return result;
    }
  },
  Session: class Session {
    constructor() {
      ReactNativeOlm.createSession();

      log('OlmSession');
      // return OlmSession;
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freeSession();
      log('free finished');
      return result;
    }

    pickle(key: string | Uint8Array) {
      log('pickle');
      const result = ReactNativeOlm.pickleSession(key);
      log('pickle finished');
      return result;
    }

    unpickle(key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = ReactNativeOlm.unpickleSession(key, pickle);
      log('unpickle FINISHED');
      return result;
    }

    create_outbound(
      account: any,
      their_identity_key: string,
      their_one_time_key: string
    ) {
      log('create_outbound');
      const result = ReactNativeOlm.createOutboundSession(
        account,
        their_identity_key,
        their_one_time_key
      );
      log('create_outbound FINISHED');
      return result;
    }

    create_inbound(account: any, one_time_key_message: string) {
      log('create_inbound');
      const result = ReactNativeOlm.createInboundSession(
        account,
        one_time_key_message
      );
      log('create_inbound FINISHED');
      return result;
    }

    create_inbound_from(
      account: any,
      identity_key: string,
      one_time_key_message: string
    ) {
      log('create_inbound_from');
      const result = ReactNativeOlm.createInboundSessionFrom(
        account,
        identity_key,
        one_time_key_message
      );
      log('create_inbound_from FINISHED');
      return result;
    }

    session_id() {
      log('session_id');
      const result = ReactNativeOlm.getSessionId();
      log('session_id FINISHED');
      return result;
    }

    has_received_message() {
      log('has_received_message');
      const result = ReactNativeOlm.sessionHasReceivedMessage();
      log('has_received_message FINISHED');
      return result;
    }

    matches_inbound(one_time_key_message: string) {
      log('matches_inbound');
      const result = ReactNativeOlm.sessionMatchesInbound(one_time_key_message);
      log('matches_inbound FINISHED');
      return result;
    }

    matches_inbound_from(identity_key: string, one_time_key_message: string) {
      log('matches_inbound_from');
      const result = ReactNativeOlm.sessionMatchesInboundFrom(
        identity_key,
        one_time_key_message
      );
      log('matches_inbound_from FINISHED');
      return result;
    }

    encrypt(plaintext: string) {
      log('encrypt');
      const result = ReactNativeOlm.encryptSession(plaintext);
      log('encrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }

      return result;
    }

    decrypt(message_type: number, message: string) {
      log('decrypt');
      const result = ReactNativeOlm.decryptSession(message_type, message);
      log('decrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }

      return result.result;
    }

    describe() {
      log('describe');
      const result = ReactNativeOlm.describeSession();
      log('describe FINISHED');
      return result;
    }
  },
  Utility: class Utility {
    constructor() {
      log('Utility');
      ReactNativeOlm.createUtility();
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freeUtility();
      log('free FINISHED');
      return result;
    }

    sha256(input: string | Uint8Array) {
      log('sha256');
      const result = ReactNativeOlm.sha256(input);
      log('sha256 FINISHED');
      return result;
    }

    ed25519_verify(
      key: string,
      message: string | Uint8Array,
      signature: string
    ) {
      log('ed25519_verify');
      const result = ReactNativeOlm.ed25519_verify(key, message, signature);
      log('ed25519_verify FINISHED');
      return result;
    }
  },
  PkSigning: class PkSigning {
    constructor() {
      log('PkSigning');
      ReactNativeOlm.createPkSigning();
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freePkSigning();
      log('free FINISHED');
      return result;
    }

    init_with_seed(seed: Uint8Array) {
      log('init_with_seed');
      const result = ReactNativeOlm.initPkSigningWithSeed(Array.from(seed));
      log('init_with_seed FINISHED');
      return result;
    }

    generate_seed() {
      log('generate_seed');
      const result = new Uint8Array(ReactNativeOlm.generatePkSigningSeed());
      log('generate_seed FINISHED');
      return result;
    }

    sign(message: string) {
      log('sign');
      const result = ReactNativeOlm.signPkSigning(message);
      log('sign FINISHED');
      return result;
    }
  },
  PkEncryption: class PkEncryption {
    constructor() {
      ReactNativeOlm.createPkEncryption();

      log('OlmPkEncryption');
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freePkEncryption();
      log('free FINISHED');
      return result;
    }

    set_recipient_key(key: string) {
      log('set_recipient_key');
      const result = ReactNativeOlm.setPkEncryptionRecipientKey(key);
      log('set_recipient_key FINISHED');
      return result;
    }

    encrypt(plaintext: string) {
      log('encrypt');
      const result = ReactNativeOlm.encryptPkEncryption(plaintext);
      log('encrypt FINISHED');
      return result;
    }
  },
  PkDecryption: class PkDecryption {
    constructor() {
      log('OlmPkDecryption');
      ReactNativeOlm.createPkDecryption();

      // return OlmPkDecryption;
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freePkDecryption();
      log('free FINISHED');
      return result;
    }

    init_with_private_key(key: Uint8Array) {
      log('init_with_private_key');
      const result = ReactNativeOlm.initPkDecryptionWithPrivateKey(
        Array.from(key)
      );
      log('init_with_private_key FINISHED');
      return result;
    }

    generate_key() {
      log('generate_key');
      const result = ReactNativeOlm.generatePkDecryptionKey();
      log('generate_key FINISHED');
      return result;
    }

    get_private_key() {
      log('get_private_key');
      const result = ReactNativeOlm.getPkDecryptionPrivateKey();
      log('get_private_key FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = ReactNativeOlm.picklePkDecryption('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = ReactNativeOlm.unpicklePkDecryption('DEFAULT_KEY', pickle);
      log('unpickle FINISHED');
      return result;
    }

    decrypt(ephemeral_key: string, mac: string, ciphertext: string) {
      log('decrypt ================>');
      const result = ReactNativeOlm.decryptPkDecryption(
        ephemeral_key,
        mac,
        ciphertext
      );
      log('decrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }

      return result.result;
    }
  },
  OutboundGroupSession: class OutboundGroupSession {
    constructor() {
      // return OlmOutboundGroupSession;
      log('OlmOutboundGroupSession');
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freeOutboundGroupSession();
      log('free FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = ReactNativeOlm.pickleOutboundGroupSession('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = ReactNativeOlm.unpickleOutboundGroupSession(
        'DEFAULT_KEY',
        pickle
      );
      log('unpickle FINISHED');
      return result;
    }

    create() {
      log('create');
      const result = ReactNativeOlm.createOutboundGroupSession();
      log('create FINISHED');
      return result;
    }

    encrypt(plaintext: string) {
      log('encrypt');
      const result = ReactNativeOlm.encryptOutboundGroupSession(plaintext);
      log('encrypt FINISHED', result);
      return result;
    }

    session_id() {
      log('session_id');
      const result = ReactNativeOlm.getOutboundGroupSessionSessionId();
      log('session_id FINISHED', result);
      return result;
    }

    session_key() {
      log('session_key');
      const result = ReactNativeOlm.getOutboundGroupSessionSessionKey();
      log('session_key FINISHED', result);
      return result;
    }

    message_index() {
      log('message_index');
      const result = ReactNativeOlm.getOutboundGroupSessionMessageIndex();
      log('message_index FINISHED', result);
      return result;
    }
  },
  InboundGroupSession: class InboundGroupSession {
    constructor() {
      // return OlmInboundGroupSession;
      log('OlmInboundGroupSession');
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freeInboundGroupSession();
      log('free FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = ReactNativeOlm.pickleInboundGroupSession('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = ReactNativeOlm.unpickleInboundGroupSession(
        'DEFAULT_KEY',
        pickle
      );
      log('unpickle FINISHED');
      return result;
    }

    create(session_key: string) {
      log('create');
      const result = ReactNativeOlm.createInboundGroupSession(session_key);
      log('create FINISHED');
      return result;
    }

    import_session(session_key: string) {
      log('import_session');
      const result = ReactNativeOlm.importInboundGroupSession(session_key);
      log('import_session FINISHED');
      if (result?.error) {
        throw new Error(result?.error);
      }
    }

    decrypt(message: string) {
      log('decrypt');
      const result = ReactNativeOlm.decryptInboundGroupSession(message);
      log('decrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result;
    }

    session_id() {
      log('session_id');
      const result = ReactNativeOlm.getInboundGroupSessionSessionId();
      log('session_id FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result.result;
    }

    first_known_index() {
      log('first_known_index');
      const result = ReactNativeOlm.getInboundGroupSessionFirstKnownIndex();
      log('first_known_index FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result.result;
    }

    export_session(message_index: number) {
      log('export_session');
      const result = ReactNativeOlm.exportInboundGroupSession(message_index);
      log('export_session FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result.result;
    }
  },
  Account: class Account {
    constructor() {
      // return OlmAccount;
      log('OlmAccount');
    }

    free() {
      log('free');
      const result = ReactNativeOlm.freeAccount();
      log('free FINISHED');
      return result;
    }

    create() {
      log('create');
      const result = ReactNativeOlm.createAccount();
      log('create FINISHED');
      return result;
    }

    identity_keys() {
      log('identity_keys');
      const result = ReactNativeOlm.identity_keys();
      log('identity_keys FINISHED');
      return result;
    }

    sign(message: string | Uint8Array) {
      log('sign');
      const result = ReactNativeOlm.signAccount(message);
      log('sign FINISHED');
      return result;
    }

    one_time_keys() {
      log('one_time_keys');
      const result = ReactNativeOlm.one_time_keys();
      log('one_time_keys FINISHED');
      return result;
    }

    mark_keys_as_published() {
      log('mark_keys_as_published');
      const result = ReactNativeOlm.mark_keys_as_published();
      log('mark_keys_as_published FINISHED');
      return result;
    }

    max_number_of_one_time_keys() {
      log('max_number_of_one_time_keys');
      const result = ReactNativeOlm.max_number_of_one_time_keys();
      log('max_number_of_one_time_keys FINISHED');
      return result;
    }

    generate_one_time_keys(number_of_keys: number) {
      log('generate_one_time_keys');
      const result = ReactNativeOlm.generate_one_time_keys(number_of_keys);
      log('generate_one_time_keys FINISHED');
      return result;
    }

    remove_one_time_keys(_session: any) {
      log('remove_one_time_keys');
      const result = ReactNativeOlm.remove_one_time_keys('session'); // FIXME LATER
      log('remove_one_time_keys FINISHED');
      return result;
    }

    generate_fallback_key() {
      log('generate_fallback_key');
      const result = ReactNativeOlm.generate_fallback_key();
      log('generate_fallback_key FINISHED');
      return result;
    }

    fallback_key() {
      log('fallback_key');
      const result = ReactNativeOlm.fallback_key();
      log('fallback_key FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = ReactNativeOlm.pickleAccount('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = ReactNativeOlm.unpickleAccount('DEFAULT_KEY', pickle);
      log('unpickle FINISHED');
      return result;
    }
  },
};

export default Olm;
