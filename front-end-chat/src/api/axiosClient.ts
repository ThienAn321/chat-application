/* eslint-disable */
import axios, { AxiosResponse } from 'axios';
import { BadRequestFieldError, HttpResponse } from '../models/http';
import AxiosResponseData from '../models/axios';
import authApi from './auth/authApi';
import env from '../config/env';
import { RefreshTokenResponse } from '../models/auth/auth';

const axiosClient = axios.create({
  baseURL: env.baseGatewayUrl,
  headers: {
    'Content-Type': 'application/json',
  },
});

const axiosRefresh = axios.create({
  baseURL: env.baseGatewayUrl,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosClient.interceptors.response.use(
  // @ts-expect-error: we want to return the different data type
  (response: AxiosResponse<AxiosResponseData>) => {
    const { status, data: responseData, headers } = response;

    const data: HttpResponse<object> = {
      status,
      ok: true,
      body: responseData,
      data: function (): { payload: any; type: 'auth/loginSuccess' } {
        throw new Error('Function not implemented.');
      },
    };

    if (headers.link) {
      data.pagination = {
        paging: 0,
        total: Number(headers['x-total-count']),
      };
    }

    return data;
  },
  async (error) => {
    const originalRequest = error.config;
    const { response } = error;
    const { status, data } = response as AxiosResponse<AxiosResponseData>;
    const fieldErrors: BadRequestFieldError = {};

    if (status === 401) {
      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken) {
        localStorage.removeItem('token');
        try {
          const refreshResponse: AxiosResponse<RefreshTokenResponse> = await authApi.refreshToken({ refreshToken });
          if (refreshResponse.status === 200) {
            localStorage.setItem('token', refreshResponse.data.accessToken);
            originalRequest.headers[
              'Authorization'
            ] = `Bearer ${refreshResponse.data.accessToken}`;
            return axiosClient(originalRequest);
          } else {
            handleExpiredToken();
          }
        } catch (error) {
          handleExpiredToken();
        }
      } else {
        handleExpiredToken();
        return Promise.reject('');
      }
    }

    if (
      typeof data?.fieldErrors === 'object' ||
      Array.isArray(data?.fieldErrors)
    ) {
      if (data?.fieldErrors?.length) {
        data.fieldErrors.forEach(({ field, messageCode }) => {
          if (fieldErrors[field]) {
            fieldErrors[field].push(messageCode);
          } else {
            fieldErrors[field] = [messageCode];
          }
        });
      }
    }

    const errorResponse: HttpResponse = {
      status,
      ok: false,
      error: {
        unauthorized: status === 401,
        badRequest: status === 400,
        notFound: status === 404,
        clientError: status >= 400 && status <= 499,
        serverError: status >= 500 && status <= 599,
        messageCode: data?.messageCode || data?.data?.messageCode,
        title: `${data?.messageCode || ''}-title`,
        errors: data?.errors,
        detail: data?.detail,
        data: data?.data,
        fieldErrors: fieldErrors,
      },
      data: function (): { payload: any; type: 'auth/loginSuccess' } {
        throw new Error('Function not implemented.');
      },
    };

    return Promise.reject(errorResponse);
  }
);

const handleExpiredToken = () => {
  localStorage.removeItem('refreshToken');
  window.location.href = '/';
  return Promise.reject('');
};

export const handleRequest = (promise: Promise<HttpResponse>) =>
  promise.then((res) => res).catch((err) => err as HttpResponse<any>);

export { axiosClient, axiosRefresh };
