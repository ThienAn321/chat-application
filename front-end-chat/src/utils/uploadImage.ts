import { getDownloadURL, ref, uploadBytesResumable } from 'firebase/storage';
import { storage } from '../config/firebase/firebase';

const uploadImage = async (
  file: File,
  setProgress: React.Dispatch<React.SetStateAction<number>>
) => {
  const date = new Date().toISOString();
  const storageRef = ref(storage, `images/${date}_${file.name}`);
  const uploadTask = uploadBytesResumable(storageRef, file);

  return new Promise<string>((resolve, reject) => {
    uploadTask.on(
      'state_changed',
      (snapshot) => {
        const progress = parseFloat(
          ((snapshot.bytesTransferred / snapshot.totalBytes) * 100).toFixed(2)
        );
        setProgress(progress);
      },
      (error) => {
        reject(`Something went wrong : ${error}`);
      },
      () => {
        getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
          resolve(downloadURL);
        });
      }
    );
  });
};

export default uploadImage;
