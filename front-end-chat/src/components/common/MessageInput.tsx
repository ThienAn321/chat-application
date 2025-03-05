import { useEffect, useRef, useState } from 'react';
import EmojiPicker from 'emoji-picker-react';
import { Theme } from 'emoji-picker-react';
import { sendMessage } from '../../utils/webSocketClient';
import { useChatRoom } from '../../context/ChatRoomContext';
import uploadImage from '../../utils/uploadImage';
import { toast } from 'react-toastify';

interface IMessageInputProps {
  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  setProgress: React.Dispatch<React.SetStateAction<number>>;
}

const MessageInput: React.FC<IMessageInputProps> = ({
  setLoading,
  setProgress,
}) => {
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
    if (!isImage && !text) {
      toast.warn('Please enter your messages');
      return;
    }
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

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>): void => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleSendMessage(false);
    }
  };

  const handleFileUpload = async (file: File): Promise<void> => {
    setLoading(true);
    setProgress(0);
    const imageUrl = await uploadImage(file, setProgress);
    setLoading(false);
    handleSendMessage(true, imageUrl);
  };

  const handleFileUploadChange = async (
    e: React.ChangeEvent<HTMLInputElement>
  ): Promise<void> => {
    const file = e.target.files?.[0];
    if (file) {
      handleFileUpload(file);
      // Reset the file input to allow the same file to be selected again
      e.target.value = '';
    }
  };

  const handleDrop = async (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    const file = e.dataTransfer.files[0];
    if (
      file &&
      (file.type.startsWith('image/') ||
        file.type === 'application/pdf' ||
        file.type ===
          'application/vnd.openxmlformats-officedocument.wordprocessingml.document' ||
        file.type ===
          'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
        file.type === 'application/msword' ||
        file.type === 'application/vnd.ms-excel')
    ) {
      handleFileUpload(file);
    }
  };

  const handlePaste = async (e: React.ClipboardEvent<HTMLInputElement>) => {
    const items = e.clipboardData.items;
    for (let i = 0; i < items.length; i++) {
      const item = items[i];
      if (item.kind === 'file' && item.type.startsWith('image/')) {
        const file = item.getAsFile();
        if (file) {
          handleFileUpload(file);
        }
      }
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
          onChange={handleFileUploadChange}
        />
      </div>
      <input
        onDrop={handleDrop}
        onDragOver={(e) => e.preventDefault()}
        onPaste={handlePaste}
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
