import { useEffect, useRef, useState } from 'react';
import { useChatRoom } from '../../context/ChatRoomContext';
import { formatDay } from '../../utils/formatDay';

const MessageArea = () => {
  const { messages, selectedChatRoom, currentUserId } = useChatRoom();
  const [previewImage, setPreviewImage] = useState<string | null>(null);
  const divRef = useRef<HTMLDivElement>(null);
  const previewImageRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (divRef.current) {
      divRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        previewImageRef.current &&
        !previewImageRef.current.contains(event.target as Node)
      ) {
        setPreviewImage(null);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleImageClick = (imageUrl: string) => {
    setPreviewImage(imageUrl);
  };

  return (
    <>
      {messages.map((message, index) => (
        <div
          key={index}
          className={`message ${
            message.senderId === currentUserId ? 'own' : ''
          }`}
        >
          {message.senderId !== currentUserId && (
            <img src={selectedChatRoom?.avatar || './avatar.png'} alt="" />
          )}
          <div className="content">
            {message.isImage && (
              <img
                src={message.content}
                onClick={() => handleImageClick(message.content)}
                alt=""
              />
            )}
            {!message.isImage && <p>{message.content}</p>}
            <span>{formatDay(message.createdAt)}</span>
          </div>
        </div>
      ))}
      <div ref={divRef} />

      {previewImage && (
        <div className="preview-image" ref={previewImageRef}>
          <img src={previewImage} alt="Preview" />
        </div>
      )}
    </>
  );
};

export default MessageArea;
