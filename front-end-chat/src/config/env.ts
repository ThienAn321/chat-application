/* eslint-disable */
interface ENV {
  [key: string]: any;
}

const env: ENV = {
  baseGatewayUrl: import.meta.env.VITE_BASE_GATEWAY_URL,
  baseGatewayWsUrl: import.meta.env.VITE_BASE_GATEWAY_WS_URL,
  fireBaseApiKey: import.meta.env.VITE_FIRE_BASE_API_KEY,
  fireBaseAuthDomain: import.meta.env.VITE_FIRE_BASE_AUTH_DOMAIN,
  fireBaseProjectId: import.meta.env.VITE_FIRE_BASE_PROJECT_ID,
  fireBaseStorageBucket: import.meta.env.VITE_FIRE_BASE_STORAGE_BUCKET,
  fireBaseMessagingSenderId: import.meta.env.VITE_FIRE_BASE_MESSAGING_SENDER_ID,
  fireBaseAppId: import.meta.env.VITE_FIRE_BASE_APP_ID,
};

export default env;