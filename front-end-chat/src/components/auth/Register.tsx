import React, { useState } from 'react';
import { RegistrationRequest } from '../../models/auth/auth';
import authApi from '../../api/auth/authApi';
import uploadImage from '../../utils/uploadImage';
import { toast } from 'react-toastify';
import { Image } from '../../models/common';

const Register = () => {
  const [avatar, setAvatar] = useState<Image>({
    file: null,
    url: '',
  });
  const [registerInfo, setRegisterInfo] = useState<RegistrationRequest>({
    email: '',
    username: '',
    password: '',
    avatar: '',
  });
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setRegisterInfo((prevInfo) => ({
      ...prevInfo,
      [name]: value,
    }));
  };

  const handleAvatar = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const file = e.target.files?.[0];
    if (file) {
      setAvatar({
        file,
        url: URL.createObjectURL(file),
      });
    }
  };

  const resetForm = (): void => {
    setAvatar({
      file: null,
      url: '',
    });
    setRegisterInfo({
      email: '',
      username: '',
      password: '',
      avatar: '',
    });
  };

  const handleRegister = async (
    e: React.FormEvent<HTMLFormElement>
  ): Promise<void> => {
    e.preventDefault();

    setIsLoading(true);
    let avatarUrl = '';
    if (avatar.file) {
      avatarUrl = await uploadImage(avatar.file);
    }

    const registrationData: RegistrationRequest = {
      ...registerInfo,
      avatar: avatarUrl,
    };

    const { ok, error } = await authApi.register(registrationData);
    if (ok) {
      toast.success('Register successfully');
      resetForm();
    } else if (error) {
      toast.error('Email already exists');
    }
    setIsLoading(false);
  };

  return (
    <div className="item">
      <h2>Create an account</h2>
      <form onSubmit={handleRegister} autoComplete="off">
        <label htmlFor="file">
          <img src={avatar.url || './avatar.png'} alt="" />
          Upload an image
        </label>
        <input
          type="file"
          id="file"
          style={{ display: 'none' }}
          onChange={handleAvatar}
        />
        <input
          type="text"
          placeholder="Username"
          name="username"
          value={registerInfo.username}
          onChange={handleInputChange}
        />
        <input
          type="text"
          placeholder="Email"
          name="email"
          value={registerInfo.email}
          onChange={handleInputChange}
        />
        <input
          type="password"
          placeholder="Password"
          name="password"
          value={registerInfo.password}
          onChange={handleInputChange}
        />
        <button disabled={isLoading}>Sign Up</button>
      </form>
    </div>
  );
};

export default Register;
