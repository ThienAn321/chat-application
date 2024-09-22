/* eslint-disable */
import '../components/init';
import SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';
import env from '../config/env';

let stompClient: CompatClient | null = null;

export const connectWebSocket = (
  userId: number,
  onMessageReceived: (message: any) => void,
  fetchChatRoom: () => Promise<void>
): CompatClient => {
  const socket = new SockJS(env.baseGatewayWsUrl);
  stompClient = Stomp.over(socket);

  stompClient.connect(
    {},
    () => {
      if (stompClient) {
        stompClient.subscribe(
          `/user/${userId}/queue/messages`,
          (message: any) => {
            if (message.body) {
              const parsedMessage = JSON.parse(message.body);
              onMessageReceived(parsedMessage);
            }
          }
        );
        stompClient.subscribe(`/topic/updateChatRoom/${userId}`, () => {
          fetchChatRoom();
        });
      }
    },
    (error: any) => {
      console.error('STOMP connection error: ', error);
    }
  );

  return stompClient;
};

export const sendMessage = (stompClient: CompatClient, message: any): void => {
  stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
};

export const disconnectWebSocket = (): void => {
  if (stompClient) {
    stompClient.disconnect(() => {
      console.log('Disconnected from WebSocket');
    });
    stompClient = null;
  }
};
