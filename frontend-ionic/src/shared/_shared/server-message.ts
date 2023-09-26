import { ServerEvent } from "../notificatinos/server-event";

export interface ServerMessage {
  id: string,
  date: string,
  type: 'EVENT',
  name: string,
  body: ServerEvent,
  meta: any
}
