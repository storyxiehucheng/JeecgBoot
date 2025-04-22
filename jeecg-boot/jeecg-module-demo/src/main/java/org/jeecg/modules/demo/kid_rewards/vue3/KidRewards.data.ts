import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '奖励名称',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '奖励描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '所需分数',
    align:"center",
    dataIndex: 'reqPoints'
   },
   {
    title: '图标',
    align:"center",
    dataIndex: 'icon',
    customRender:render.renderImage,
   },
   {
    title: '是否启用',
    align:"center",
    dataIndex: 'status',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '库存数量',
    align:"center",
    dataIndex: 'stock'
   },
   {
    title: '是否限时',
    align:"center",
    dataIndex: 'isLimited',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '生效日期',
    align:"center",
    dataIndex: 'startTime'
   },
   {
    title: '结束日期',
    align:"center",
    dataIndex: 'endTime'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '奖励名称',
    field: 'title',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入奖励名称!'},
          ];
     },
  },
  {
    label: '奖励描述',
    field: 'description',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入奖励描述!'},
          ];
     },
  },
  {
    label: '所需分数',
    field: 'reqPoints',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入所需分数!'},
          ];
     },
  },
  {
    label: '图标',
    field: 'icon',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入图标!'},
          ];
     },
  },
  {
    label: '是否启用',
    field: 'status',
    defaultValue: "Y",
     component: 'JSwitch',
     componentProps:{
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否启用!'},
          ];
     },
  },
  {
    label: '库存数量',
    field: 'stock',
    defaultValue: 999,
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入库存数量!'},
          ];
     },
  },
  {
    label: '是否限时',
    field: 'isLimited',
    defaultValue: "N",
     component: 'JSwitch',
     componentProps:{
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否限时!'},
          ];
     },
  },
  {
    label: '生效日期',
    field: 'startTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '结束日期',
    field: 'endTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  title: {title: '奖励名称',order: 0,view: 'text', type: 'string',},
  description: {title: '奖励描述',order: 1,view: 'textarea', type: 'string',},
  reqPoints: {title: '所需分数',order: 2,view: 'number', type: 'number',},
  icon: {title: '图标',order: 3,view: 'image', type: 'string',},
  status: {title: '是否启用',order: 4,view: 'switch', type: 'string',},
  stock: {title: '库存数量',order: 5,view: 'number', type: 'number',},
  isLimited: {title: '是否限时',order: 6,view: 'switch', type: 'string',},
  startTime: {title: '生效日期',order: 7,view: 'datetime', type: 'string',},
  endTime: {title: '结束日期',order: 8,view: 'datetime', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}