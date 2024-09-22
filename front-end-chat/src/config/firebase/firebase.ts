import { initializeApp } from 'firebase/app';
import { getStorage } from 'firebase/storage';
import env from '../env';

const firebaseConfig = {
  apiKey: env.fireBaseApiKey,
  authDomain: env.fireBaseAuthDomain,
  projectId: env.fireBaseProjectId,
  storageBucket: env.fireBaseStorageBucket,
  messagingSenderId: env.fireBaseMessagingSenderId,
  appId: env.fireBaseAppId,
};

initializeApp(firebaseConfig);

export const storage = getStorage();
