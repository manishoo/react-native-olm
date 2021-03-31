import { NativeModules } from 'react-native';

let {
  OlmSAS,
  OlmSession,
  OlmUtility,
  OlmPkSigning,
  OlmPkEncryption,
  OlmPkDecryption,
  OlmOutboundGroupSession,
  OlmInboundGroupSession,
  OlmAccount,
  OlmManager,
} = NativeModules;

OlmManager.init();

const log = (..._args: any[]) => {
  // console.log(..._args);
};

const Olm = {
  init: OlmManager.init,
  get_library_version: OlmManager.getOlmLibVersion,
  PRIVATE_KEY_LENGTH: OlmPkDecryption.PRIVATE_KEY_LENGTH(),
  SAS: class SAS {
    constructor() {
      OlmManager.init();
      OlmSAS.create();
    }

    free() {
      log('free');
      const result = OlmSAS.free();
      log('free FINISHED');

      return result;
    }

    get_pubkey() {
      log('logget_pubkey');
      const result = OlmSAS.get_pubkey();
      log('get_pubkey FINISHED');

      return result;
    }

    set_their_key(their_key: string) {
      log('set_their_key');
      const result = OlmSAS.set_their_key(their_key);
      log('set_their_key FINISHED');

      return result;
    }

    generate_bytes(info: string, length: number) {
      log('generate_bytes');
      const result = new Uint8Array(OlmSAS.generate_bytes(info, length));
      log('generate_bytes FINISHED');

      return result;
    }

    calculate_mac(input: string, info: string) {
      log('calculate_mac');
      const result = OlmSAS.calculate_mac(input, info);
      log('calculate_mac FINISHED');

      return result;
    }

    calculate_mac_long_kdf(input: string, info: string) {
      log('calculate_mac_long_kdf');
      const result = OlmSAS.calculate_mac_long_kdf(input, info);
      log('calculate_mac_long_kdf FINISHED');

      return result;
    }
  },
  Session: class Session {
    constructor() {
      OlmSession.create();

      log('OlmSession');
      // return OlmSession;
    }

    free() {
      log('free');
      const result = OlmSession.free();
      log('free finished');
      return result;
    }

    pickle(key: string | Uint8Array) {
      log('pickle');
      const result = OlmSession.pickle(key);
      log('pickle finished');
      return result;
    }

    unpickle(key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = OlmSession.unpickle(key, pickle);
      log('unpickle FINISHED');
      return result;
    }

    create_outbound(
      account: any,
      their_identity_key: string,
      their_one_time_key: string
    ) {
      log('create_outbound');
      const result = OlmSession.create_outbound(
        account,
        their_identity_key,
        their_one_time_key
      );
      log('create_outbound FINISHED');
      return result;
    }

    create_inbound(account: any, one_time_key_message: string) {
      log('create_inbound');
      const result = OlmSession.create_inbound(account, one_time_key_message);
      log('create_inbound FINISHED');
      return result;
    }

    create_inbound_from(
      account: any,
      identity_key: string,
      one_time_key_message: string
    ) {
      log('create_inbound_from');
      const result = OlmSession.create_inbound_from(
        account,
        identity_key,
        one_time_key_message
      );
      log('create_inbound_from FINISHED');
      return result;
    }

    session_id() {
      log('session_id');
      const result = OlmSession.session_id();
      log('session_id FINISHED');
      return result;
    }

    has_received_message() {
      log('has_received_message');
      const result = OlmSession.has_received_message();
      log('has_received_message FINISHED');
      return result;
    }

    matches_inbound(one_time_key_message: string) {
      log('matches_inbound');
      const result = OlmSession.matches_inbound(one_time_key_message);
      log('matches_inbound FINISHED');
      return result;
    }

    matches_inbound_from(identity_key: string, one_time_key_message: string) {
      log('matches_inbound_from');
      const result = OlmSession.matches_inbound_from(
        identity_key,
        one_time_key_message
      );
      log('matches_inbound_from FINISHED');
      return result;
    }

    encrypt(plaintext: string) {
      log('encrypt');
      const result = OlmSession.encrypt(plaintext);
      log('encrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }

      return result;
    }

    decrypt(message_type: number, message: string) {
      log('decrypt');
      const result = OlmSession.decrypt(message_type, message);
      log('decrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }

      return result.result;
    }

    describe() {
      log('describe');
      const result = OlmSession.describe();
      log('describe FINISHED');
      return result;
    }
  },
  Utility: class Utility {
    constructor() {
      OlmUtility.create();

      return OlmUtility;
    }
  },
  PkSigning: class PkSigning {
    constructor() {
      log('PkSigning');
      OlmPkSigning.create();
    }

    free() {
      log('free');
      const result = OlmPkSigning.free();
      log('free FINISHED');
      return result;
    }

    init_with_seed(seed: Uint8Array) {
      log('init_with_seed');
      const result = OlmPkSigning.init_with_seed(Array.from(seed));
      log('init_with_seed FINISHED');
      return result;
    }

    generate_seed() {
      log('generate_seed');
      const result = new Uint8Array(OlmPkSigning.generate_seed());
      log('generate_seed FINISHED');
      return result;
    }

    sign(message: string) {
      log('sign');
      const result = OlmPkSigning.sign(message);
      log('sign FINISHED');
      return result;
    }
  },
  PkEncryption: class PkEncryption {
    constructor() {
      OlmPkEncryption.create();

      log('OlmPkEncryption');
      // return OlmPkEncryption;
    }

    free() {
      log('free');
      const result = OlmPkEncryption.free();
      log('free FINISHED');
      return result;
    }

    set_recipient_key(key: string) {
      log('set_recipient_key');
      const result = OlmPkEncryption.set_recipient_key(key);
      log('set_recipient_key FINISHED');
      return result;
    }

    encrypt(plaintext: string) {
      log('encrypt');
      const result = OlmPkEncryption.encrypt(plaintext);
      log('encrypt FINISHED');
      return result;
    }
  },
  PkDecryption: class PkDecryption {
    constructor() {
      log('OlmPkDecryption');
      OlmPkDecryption.create();

      // return OlmPkDecryption;
    }

    free() {
      log('free');
      const result = OlmPkDecryption.free();
      log('free FINISHED');
      return result;
    }

    init_with_private_key(key: Uint8Array) {
      log('init_with_private_key');
      const result = OlmPkDecryption.init_with_private_key(Array.from(key));
      log('init_with_private_key FINISHED');
      return result;
    }

    generate_key() {
      log('generate_key');
      const result = OlmPkDecryption.generate_key();
      log('generate_key FINISHED');
      return result;
    }

    get_private_key() {
      log('get_private_key');
      const result = OlmPkDecryption.get_private_key();
      log('get_private_key FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = OlmPkDecryption.pickle('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = OlmPkDecryption.unpickle('DEFAULT_KEY', pickle);
      log('unpickle FINISHED');
      return result;
    }

    decrypt(ephemeral_key: string, mac: string, ciphertext: string) {
      log('decrypt ================>');
      const result = OlmPkDecryption.decrypt(ephemeral_key, mac, ciphertext);
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
      const result = OlmOutboundGroupSession.free();
      log('free FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = OlmOutboundGroupSession.pickle('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = OlmOutboundGroupSession.unpickle('DEFAULT_KEY', pickle);
      log('unpickle FINISHED');
      return result;
    }

    create() {
      log('create');
      const result = OlmOutboundGroupSession.create();
      log('create FINISHED');
      return result;
    }

    encrypt(plaintext: string) {
      log('encrypt');
      const result = OlmOutboundGroupSession.encrypt(plaintext);
      log('encrypt FINISHED', result);
      return result;
    }

    session_id() {
      log('session_id');
      const result = OlmOutboundGroupSession.session_id();
      log('session_id FINISHED', result);
      return result;
    }

    session_key() {
      log('session_key');
      const result = OlmOutboundGroupSession.session_key();
      log('session_key FINISHED', result);
      return result;
    }

    message_index() {
      log('message_index');
      const result = OlmOutboundGroupSession.message_index();
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
      const result = OlmInboundGroupSession.free();
      log('free FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = OlmInboundGroupSession.pickle('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = OlmInboundGroupSession.unpickle('DEFAULT_KEY', pickle);
      log('unpickle FINISHED');
      return result;
    }

    create(session_key: string) {
      log('create');
      const result = OlmInboundGroupSession.create(session_key);
      log('create FINISHED');
      return result;
    }

    import_session(session_key: string) {
      log('import_session');
      const result = OlmInboundGroupSession.import_session(session_key);
      log('import_session FINISHED');
      if (result?.error) {
        throw new Error(result?.error);
      }
    }

    decrypt(message: string) {
      log('decrypt');
      const result = OlmInboundGroupSession.decrypt(message);
      log('decrypt FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result;
    }

    session_id() {
      log('session_id');
      const result = OlmInboundGroupSession.session_id();
      log('session_id FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result.result;
    }

    first_known_index() {
      log('first_known_index');
      const result = OlmInboundGroupSession.first_known_index();
      log('first_known_index FINISHED');
      if (result?.error) {
        throw new Error(result?.errorMessage);
      }
      return result.result;
    }

    export_session(message_index: number) {
      log('export_session');
      const result = OlmInboundGroupSession.export_session(message_index);
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
      const result = OlmAccount.free();
      log('free FINISHED');
      return result;
    }

    create() {
      log('create');
      const result = OlmAccount.create();
      log('create FINISHED');
      return result;
    }

    identity_keys() {
      log('identity_keys');
      const result = OlmAccount.identity_keys();
      log('identity_keys FINISHED');
      return result;
    }

    sign(message: string | Uint8Array) {
      log('sign');
      const result = OlmAccount.sign(message);
      log('sign FINISHED');
      return result;
    }

    one_time_keys() {
      log('one_time_keys');
      const result = OlmAccount.one_time_keys();
      log('one_time_keys FINISHED');
      return result;
    }

    mark_keys_as_published() {
      log('mark_keys_as_published');
      const result = OlmAccount.mark_keys_as_published();
      log('mark_keys_as_published FINISHED');
      return result;
    }

    max_number_of_one_time_keys() {
      log('max_number_of_one_time_keys');
      const result = OlmAccount.max_number_of_one_time_keys();
      log('max_number_of_one_time_keys FINISHED');
      return result;
    }

    generate_one_time_keys(number_of_keys: number) {
      log('generate_one_time_keys');
      const result = OlmAccount.generate_one_time_keys(number_of_keys);
      log('generate_one_time_keys FINISHED');
      return result;
    }

    remove_one_time_keys(_session: any) {
      log('remove_one_time_keys');
      const result = OlmAccount.remove_one_time_keys('session'); // FIXME LATER
      log('remove_one_time_keys FINISHED');
      return result;
    }

    generate_fallback_key() {
      log('generate_fallback_key');
      const result = OlmAccount.generate_fallback_key();
      log('generate_fallback_key FINISHED');
      return result;
    }

    fallback_key() {
      log('fallback_key');
      const result = OlmAccount.fallback_key();
      log('fallback_key FINISHED');
      return result;
    }

    pickle(_key: string | Uint8Array) {
      log('pickle');
      const result = OlmAccount.pickle('DEFAULT_KEY');
      log('pickle FINISHED');
      return result;
    }

    unpickle(_key: string | Uint8Array, pickle: string) {
      log('unpickle');
      const result = OlmAccount.unpickle('DEFAULT_KEY', pickle);
      log('unpickle FINISHED');
      return result;
    }
  },
  Manager: class Manager {
    constructor() {
      return OlmManager;
    }
  },
};

export default Olm;
