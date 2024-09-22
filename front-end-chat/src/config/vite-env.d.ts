interface ImportMetaEnv {
  readonly VITE_BASE_GATEWAY_URL: string;
  readonly VITE_BASE_GATEWAY_WS_URL: string;
  readonly VITE_FIRE_BASE_API_KEY: string;
  readonly VITE_FIRE_BASE_AUTH_DOMAIN: string;
  readonly VITE_FIRE_BASE_PROJECT_ID: string;
  readonly VITE_FIRE_BASE_STORAGE_BUCKET: string;
  readonly VITE_FIRE_BASE_MESSAGING_SENDER_ID: string;
  readonly VITE_FIRE_BASE_APP_ID: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}