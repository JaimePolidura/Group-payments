FROM node:latest as node
WORKDIR /app
COPY ./ /app/

EXPOSE 80
RUN npm ci
RUN npm run build --prod
RUN mv /app/dist/frontend/* /app/dist/

FROM nginx:1.13.8-alpine
COPY --from=node /app/dist/ /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
