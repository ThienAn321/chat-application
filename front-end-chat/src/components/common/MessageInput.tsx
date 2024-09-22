import { useEffect, useRef, useState } from 'react';
import EmojiPicker from 'emoji-picker-react';
import { Theme } from 'emoji-picker-react';
import { sendMessage } from '../../utils/webSocketClient';
import { useChatRoom } from '../../context/ChatRoomContext';
import uploadImage from '../../utils/uploadImage';

const MessageInput = () => {
  const [open, setOpen] = useState<boolean>(false);
  const [text, setText] = useState<string>('');
  const emojiPickerRef = useRef<HTMLDivElement | null>(null);
  const emojiButtonRef = useRef<HTMLImageElement | null>(null);
  const inputRef = useRef<HTMLInputElement>(null);
  const { stompClient, selectedChatRoom, currentUserId } = useChatRoom();

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        emojiPickerRef.current &&
        !emojiPickerRef.current.contains(event.target as Node) &&
        emojiButtonRef.current &&
        !emojiButtonRef.current.contains(event.target as Node)
      ) {
        setOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleEmojiToggle = (): void => {
    setOpen((prev) => !prev);
  };

  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setText(e.target.value);
  };

  const handleEmojiClick = (emoji: { emoji: string }): void => {
    setText((prev) => prev + emoji.emoji);
    setOpen(false);
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  const handleSendMessage = (
    isImage: boolean = false,
    imageUrl?: string
  ): void => {
    if (stompClient && selectedChatRoom) {
      const message = {
        chatRoomId: selectedChatRoom.chatRoomId,
        senderId: currentUserId,
        recipientId: selectedChatRoom.userId,
        content: isImage ? imageUrl : text,
        isImage,
      };
      sendMessage(stompClient, message);
      setText('');
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleSendMessage(false);
    }
  };

  const handleImageUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const imageUrl = await uploadImage(file);
      handleSendMessage(true, imageUrl);
    }
  };

  return (
    <>
      <div className="icons">
        <label htmlFor="file">
          <img src="./img.png" alt="" />
        </label>
        <input
          type="file"
          id="file"
          style={{ display: 'none' }}
          onChange={handleImageUpload}
        />
      </div>
      <input
        className="message-input"
        type="text"
        placeholder="Type a message..."
        value={text}
        onChange={handleTextChange}
        onKeyDown={handleKeyDown}
        maxLength={500}
        ref={inputRef}
      />
      <div className="emoji">
        <img
          src="./emoji.png"
          alt=""
          onClick={handleEmojiToggle}
          ref={emojiButtonRef}
        />
        {open && (
          <div ref={emojiPickerRef} className="emoji-picker">
            <EmojiPicker theme={Theme.DARK} onEmojiClick={handleEmojiClick} />
          </div>
        )}
      </div>
      <button className="send-btn" onClick={() => handleSendMessage()}>
        Send
      </button>
    </>
  );
};

export default MessageInput;
