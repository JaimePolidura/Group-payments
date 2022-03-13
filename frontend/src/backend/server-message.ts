import {ServerEvent} from "./eventlistener/events/server-event";

export interface ServerMessage {
  id: string,
  date: string,
  type: 'EVENT',
  name: string,
  body: ServerEvent,
  meta: any
}
