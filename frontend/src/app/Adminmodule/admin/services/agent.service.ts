import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agent } from '../models/agent.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminAgentService {
  private apiUrl = 'http://localhost:8085/admin/agents';

  constructor(private http: HttpClient) {}



    getAllAgents():Observable<any>{
      return this.http.get<any>(this.apiUrl+"/all")
    }


    deleteAgent(agentId:number):Observable<string>{
        return this.http.delete(this.apiUrl+"/delete/"+agentId,{responseType:'text'})
    }


    createAgent(agent:Agent):Observable<any>{
        return this.http.post<any>(this.apiUrl+"/create",agent)
    }
}