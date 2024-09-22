import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './scss/app.scss';
import { Provider } from 'react-redux';
import store from './redux/store.ts';
import { ChatRoomProvider } from './context/ChatRoomContext.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <ChatRoomProvider>
        <App />
      </ChatRoomProvider>
    </Provider>
  </StrictMode>
);
