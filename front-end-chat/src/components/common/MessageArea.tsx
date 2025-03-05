import { useEffect, useRef, useState } from 'react';
import { useChatRoom } from '../../context/ChatRoomContext';
import { formatDay } from '../../utils/formatDay';
import {
  checkIsFile,
  getFileName,
  getFileTypeFromUrl,
  previewFile,
} from '../../utils/fileUtils';

interface IMessageAreaProps {
  loading: boolean;
  progress: number;
}

const MessageArea: React.FC<IMessageAreaProps> = ({ loading, progress }) => {
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
        previewImageRef.current.classList.add('hidden');
        setTimeout(() => {
          setPreviewImage(null);
        }, 300);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleImageClick = (imageUrl: string) => {
    const url = previewFile(imageUrl);
    if (url) {
      setPreviewImage(url);
    }
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

          {checkIsFile(message.content) === 'file' ? (
            <div className="content file">
              {message.isImage && (
                <div className="content-file-container">
                  <div
                    className="content-file-holder"
                    onClick={() => handleImageClick(message.content)}
                  >
                    <img src={getFileTypeFromUrl(message.content)} alt="" />
                    <span>{getFileName(message.content)}</span>
                  </div>
                </div>
              )}
              <span>{formatDay(message.createdAt)}</span>
            </div>
          ) : (
            <div className="content">
              {message.isImage && (
                <img
                  src={getFileTypeFromUrl(message.content)}
                  onClick={() => handleImageClick(message.content)}
                  alt=""
                />
              )}
              {!message.isImage && <p>{message.content}</p>}
              <span>{formatDay(message.createdAt)}</span>
            </div>
          )}
        </div>
      ))}

      {previewImage && (
        <div className="preview-image" ref={previewImageRef}>
          <img src={previewImage} alt="Preview" />
        </div>
      )}

      {loading && (
        <div className="message own">
          <div className="content upload-image">
            <span>{progress}%</span>
            <progress className="progress-bar" value={progress} max={100}>
              {progress}%
            </progress>
          </div>
        </div>
      )}
      <div ref={divRef} />
    </>
  );
};

export default MessageArea;
