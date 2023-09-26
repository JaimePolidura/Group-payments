export abstract class ToastService {
  public abstract info: (title: string, body: string, onClick?: () => void) => void;
  public abstract success: (title: string, body: string, onClick?: () => void) => void;
  public abstract warning: (title: string, body: string, onClick?: () => void) => void;
  public abstract error: (title: string, body: string, onClick?: () => void) => void;
}
